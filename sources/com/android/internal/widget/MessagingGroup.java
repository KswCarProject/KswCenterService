package com.android.internal.widget;

import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pools;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.widget.MessagingLinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RemoteViews.RemoteView
public class MessagingGroup extends LinearLayout implements MessagingLinearLayout.MessagingChild {
    private static Pools.SimplePool<MessagingGroup> sInstancePool = new Pools.SynchronizedPool(10);
    /* access modifiers changed from: private */
    public ArrayList<MessagingMessage> mAddedMessages = new ArrayList<>();
    private Icon mAvatarIcon;
    private CharSequence mAvatarName = "";
    private String mAvatarSymbol = "";
    private ImageView mAvatarView;
    private Point mDisplaySize = new Point();
    private boolean mFirstLayout;
    private ViewGroup mImageContainer;
    private boolean mImagesAtEnd;
    private boolean mIsHidingAnimated;
    private MessagingImageMessage mIsolatedMessage;
    private int mLayoutColor;
    private MessagingLinearLayout mMessageContainer;
    private List<MessagingMessage> mMessages;
    private boolean mNeedsGeneratedAvatar;
    private Person mSender;
    private ImageFloatingTextView mSenderName;
    private ProgressBar mSendingSpinner;
    private View mSendingSpinnerContainer;
    private int mSendingTextColor;
    private int mTextColor;
    private boolean mTransformingImages;

    public MessagingGroup(Context context) {
        super(context);
    }

    public MessagingGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessagingGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MessagingGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageContainer = (MessagingLinearLayout) findViewById(R.id.group_message_container);
        this.mSenderName = (ImageFloatingTextView) findViewById(R.id.message_name);
        this.mAvatarView = (ImageView) findViewById(R.id.message_icon);
        this.mImageContainer = (ViewGroup) findViewById(R.id.messaging_group_icon_container);
        this.mSendingSpinner = (ProgressBar) findViewById(R.id.messaging_group_sending_progress);
        this.mSendingSpinnerContainer = findViewById(R.id.messaging_group_sending_progress_container);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mDisplaySize.x = displayMetrics.widthPixels;
        this.mDisplaySize.y = displayMetrics.heightPixels;
    }

    public void updateClipRect() {
        Rect clipRect;
        if (this.mSenderName.getVisibility() == 8 || this.mTransformingImages) {
            clipRect = null;
        } else {
            ViewGroup parent = (ViewGroup) this.mSenderName.getParent();
            int top = (getDistanceFromParent(this.mSenderName, parent) - getDistanceFromParent(this.mMessageContainer, parent)) + this.mSenderName.getHeight();
            int size = Math.max(this.mDisplaySize.x, this.mDisplaySize.y);
            clipRect = new Rect(0, top, size, size);
        }
        this.mMessageContainer.setClipBounds(clipRect);
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getDistanceFromParent(android.view.View r6, android.view.ViewGroup r7) {
        /*
            r5 = this;
            r0 = 0
            r1 = r0
            r0 = r6
        L_0x0003:
            if (r0 == r7) goto L_0x001a
            float r2 = (float) r1
            int r3 = r0.getTop()
            float r3 = (float) r3
            float r4 = r0.getTranslationY()
            float r3 = r3 + r4
            float r2 = r2 + r3
            int r1 = (int) r2
            android.view.ViewParent r2 = r0.getParent()
            r0 = r2
            android.view.View r0 = (android.view.View) r0
            goto L_0x0003
        L_0x001a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.MessagingGroup.getDistanceFromParent(android.view.View, android.view.ViewGroup):int");
    }

    public void setSender(Person sender, CharSequence nameOverride) {
        this.mSender = sender;
        if (nameOverride == null) {
            nameOverride = sender.getName();
        }
        this.mSenderName.setText(nameOverride);
        int i = 0;
        this.mNeedsGeneratedAvatar = sender.getIcon() == null;
        if (!this.mNeedsGeneratedAvatar) {
            setAvatar(sender.getIcon());
        }
        this.mAvatarView.setVisibility(0);
        ImageFloatingTextView imageFloatingTextView = this.mSenderName;
        if (TextUtils.isEmpty(nameOverride)) {
            i = 8;
        }
        imageFloatingTextView.setVisibility(i);
    }

    public void setSending(boolean sending) {
        int visibility = sending ? 0 : 8;
        if (this.mSendingSpinnerContainer.getVisibility() != visibility) {
            this.mSendingSpinnerContainer.setVisibility(visibility);
            updateMessageColor();
        }
    }

    private int calculateSendingTextColor() {
        TypedValue alphaValue = new TypedValue();
        this.mContext.getResources().getValue((int) R.dimen.notification_secondary_text_disabled_alpha, alphaValue, true);
        return Color.valueOf((float) Color.red(this.mTextColor), (float) Color.green(this.mTextColor), (float) Color.blue(this.mTextColor), alphaValue.getFloat()).toArgb();
    }

    public void setAvatar(Icon icon) {
        this.mAvatarIcon = icon;
        this.mAvatarView.setImageIcon(icon);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.android.internal.widget.MessagingGroup createGroup(com.android.internal.widget.MessagingLinearLayout r4) {
        /*
            android.util.Pools$SimplePool<com.android.internal.widget.MessagingGroup> r0 = sInstancePool
            java.lang.Object r0 = r0.acquire()
            com.android.internal.widget.MessagingGroup r0 = (com.android.internal.widget.MessagingGroup) r0
            if (r0 != 0) goto L_0x0022
            android.content.Context r1 = r4.getContext()
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
            r2 = 17367206(0x10900a6, float:2.516339E-38)
            r3 = 0
            android.view.View r1 = r1.inflate((int) r2, (android.view.ViewGroup) r4, (boolean) r3)
            r0 = r1
            com.android.internal.widget.MessagingGroup r0 = (com.android.internal.widget.MessagingGroup) r0
            android.view.View$OnLayoutChangeListener r1 = com.android.internal.widget.MessagingLayout.MESSAGING_PROPERTY_ANIMATOR
            r0.addOnLayoutChangeListener(r1)
        L_0x0022:
            r4.addView(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.MessagingGroup.createGroup(com.android.internal.widget.MessagingLinearLayout):com.android.internal.widget.MessagingGroup");
    }

    public void removeMessage(MessagingMessage messagingMessage) {
        View view = messagingMessage.getView();
        boolean wasShown = view.isShown();
        ViewGroup messageParent = (ViewGroup) view.getParent();
        if (messageParent != null) {
            messageParent.removeView(view);
            Runnable recycleRunnable = new Runnable(view, messagingMessage) {
                private final /* synthetic */ View f$1;
                private final /* synthetic */ MessagingMessage f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    MessagingGroup.lambda$removeMessage$0(ViewGroup.this, this.f$1, this.f$2);
                }
            };
            if (!wasShown || MessagingLinearLayout.isGone(view)) {
                recycleRunnable.run();
                return;
            }
            messageParent.addTransientView(view, 0);
            performRemoveAnimation(view, recycleRunnable);
        }
    }

    static /* synthetic */ void lambda$removeMessage$0(ViewGroup messageParent, View view, MessagingMessage messagingMessage) {
        messageParent.removeTransientView(view);
        messagingMessage.recycle();
    }

    public void recycle() {
        if (this.mIsolatedMessage != null) {
            this.mImageContainer.removeView(this.mIsolatedMessage);
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            MessagingMessage message = this.mMessages.get(i);
            this.mMessageContainer.removeView(message.getView());
            message.recycle();
        }
        setAvatar((Icon) null);
        this.mAvatarView.setAlpha(1.0f);
        this.mAvatarView.setTranslationY(0.0f);
        this.mSenderName.setAlpha(1.0f);
        this.mSenderName.setTranslationY(0.0f);
        setAlpha(1.0f);
        this.mIsolatedMessage = null;
        this.mMessages = null;
        this.mAddedMessages.clear();
        this.mFirstLayout = true;
        MessagingPropertyAnimator.recycle(this);
        sInstancePool.release(this);
    }

    public void removeGroupAnimated(Runnable endAction) {
        performRemoveAnimation(this, new Runnable(endAction) {
            private final /* synthetic */ Runnable f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                MessagingGroup.lambda$removeGroupAnimated$1(MessagingGroup.this, this.f$1);
            }
        });
    }

    public static /* synthetic */ void lambda$removeGroupAnimated$1(MessagingGroup messagingGroup, Runnable endAction) {
        messagingGroup.setAlpha(1.0f);
        MessagingPropertyAnimator.setToLaidOutPosition(messagingGroup);
        if (endAction != null) {
            endAction.run();
        }
    }

    public void performRemoveAnimation(View message, Runnable endAction) {
        performRemoveAnimation(message, -message.getHeight(), endAction);
    }

    private void performRemoveAnimation(View view, int disappearTranslation, Runnable endAction) {
        MessagingPropertyAnimator.startLocalTranslationTo(view, disappearTranslation, MessagingLayout.FAST_OUT_LINEAR_IN);
        MessagingPropertyAnimator.fadeOut(view, endAction);
    }

    public CharSequence getSenderName() {
        return this.mSenderName.getText();
    }

    public static void dropCache() {
        sInstancePool = new Pools.SynchronizedPool(10);
    }

    public int getMeasuredType() {
        if (this.mIsolatedMessage != null) {
            return 1;
        }
        boolean hasNormal = false;
        int i = this.mMessageContainer.getChildCount() - 1;
        while (true) {
            boolean tooSmall = false;
            if (i < 0) {
                return 0;
            }
            View child = this.mMessageContainer.getChildAt(i);
            if (child.getVisibility() != 8 && (child instanceof MessagingLinearLayout.MessagingChild)) {
                int type = ((MessagingLinearLayout.MessagingChild) child).getMeasuredType();
                if (type == 2) {
                    tooSmall = true;
                }
                if (tooSmall || ((MessagingLinearLayout.LayoutParams) child.getLayoutParams()).hide) {
                    if (hasNormal) {
                        return 1;
                    }
                    return 2;
                } else if (type == 1) {
                    return 1;
                } else {
                    hasNormal = true;
                }
            }
            i--;
        }
    }

    public int getConsumedLines() {
        int result = 0;
        for (int i = 0; i < this.mMessageContainer.getChildCount(); i++) {
            View child = this.mMessageContainer.getChildAt(i);
            if (child instanceof MessagingLinearLayout.MessagingChild) {
                result += ((MessagingLinearLayout.MessagingChild) child).getConsumedLines();
            }
        }
        return (this.mIsolatedMessage != null ? Math.max(result, 1) : result) + 1;
    }

    public void setMaxDisplayedLines(int lines) {
        this.mMessageContainer.setMaxDisplayedLines(lines);
    }

    public void hideAnimated() {
        setIsHidingAnimated(true);
        removeGroupAnimated(new Runnable() {
            public final void run() {
                MessagingGroup.this.setIsHidingAnimated(false);
            }
        });
    }

    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    /* access modifiers changed from: private */
    public void setIsHidingAnimated(boolean isHiding) {
        ViewParent parent = getParent();
        this.mIsHidingAnimated = isHiding;
        invalidate();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).invalidate();
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public Icon getAvatarSymbolIfMatching(CharSequence avatarName, String avatarSymbol, int layoutColor) {
        if (!this.mAvatarName.equals(avatarName) || !this.mAvatarSymbol.equals(avatarSymbol) || layoutColor != this.mLayoutColor) {
            return null;
        }
        return this.mAvatarIcon;
    }

    public void setCreatedAvatar(Icon cachedIcon, CharSequence avatarName, String avatarSymbol, int layoutColor) {
        if (!this.mAvatarName.equals(avatarName) || !this.mAvatarSymbol.equals(avatarSymbol) || layoutColor != this.mLayoutColor) {
            setAvatar(cachedIcon);
            this.mAvatarSymbol = avatarSymbol;
            setLayoutColor(layoutColor);
            this.mAvatarName = avatarName;
        }
    }

    public void setTextColors(int senderTextColor, int messageTextColor) {
        this.mTextColor = messageTextColor;
        this.mSendingTextColor = calculateSendingTextColor();
        updateMessageColor();
        this.mSenderName.setTextColor(senderTextColor);
    }

    public void setLayoutColor(int layoutColor) {
        if (layoutColor != this.mLayoutColor) {
            this.mLayoutColor = layoutColor;
            this.mSendingSpinner.setIndeterminateTintList(ColorStateList.valueOf(this.mLayoutColor));
        }
    }

    private void updateMessageColor() {
        if (this.mMessages != null) {
            int color = this.mSendingSpinnerContainer.getVisibility() == 0 ? this.mSendingTextColor : this.mTextColor;
            for (MessagingMessage message : this.mMessages) {
                message.setColor(message.getMessage().isRemoteInputHistory() ? color : this.mTextColor);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: com.android.internal.widget.MessagingMessage} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: com.android.internal.widget.MessagingImageMessage} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.android.internal.widget.MessagingImageMessage} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMessages(java.util.List<com.android.internal.widget.MessagingMessage> r10) {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = r0
            r0 = r2
        L_0x0005:
            int r4 = r10.size()
            if (r0 >= r4) goto L_0x0082
            java.lang.Object r4 = r10.get(r0)
            com.android.internal.widget.MessagingMessage r4 = (com.android.internal.widget.MessagingMessage) r4
            com.android.internal.widget.MessagingGroup r5 = r4.getGroup()
            if (r5 == r9) goto L_0x001f
            r4.setMessagingGroup(r9)
            java.util.ArrayList<com.android.internal.widget.MessagingMessage> r5 = r9.mAddedMessages
            r5.add(r4)
        L_0x001f:
            boolean r5 = r4 instanceof com.android.internal.widget.MessagingImageMessage
            boolean r6 = r9.mImagesAtEnd
            if (r6 == 0) goto L_0x002b
            if (r5 == 0) goto L_0x002b
            r1 = r4
            com.android.internal.widget.MessagingImageMessage r1 = (com.android.internal.widget.MessagingImageMessage) r1
            goto L_0x007f
        L_0x002b:
            com.android.internal.widget.MessagingLinearLayout r6 = r9.mMessageContainer
            boolean r6 = r9.removeFromParentIfDifferent(r4, r6)
            if (r6 == 0) goto L_0x0057
            android.view.View r6 = r4.getView()
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            if (r6 == 0) goto L_0x004e
            boolean r7 = r6 instanceof com.android.internal.widget.MessagingLinearLayout.LayoutParams
            if (r7 != 0) goto L_0x004e
            android.view.View r7 = r4.getView()
            com.android.internal.widget.MessagingLinearLayout r8 = r9.mMessageContainer
            com.android.internal.widget.MessagingLinearLayout$LayoutParams r8 = r8.generateDefaultLayoutParams()
            r7.setLayoutParams(r8)
        L_0x004e:
            com.android.internal.widget.MessagingLinearLayout r7 = r9.mMessageContainer
            android.view.View r8 = r4.getView()
            r7.addView((android.view.View) r8, (int) r3)
        L_0x0057:
            if (r5 == 0) goto L_0x005f
            r6 = r4
            com.android.internal.widget.MessagingImageMessage r6 = (com.android.internal.widget.MessagingImageMessage) r6
            r6.setIsolated(r2)
        L_0x005f:
            com.android.internal.widget.MessagingLinearLayout r6 = r9.mMessageContainer
            android.view.View r7 = r4.getView()
            int r6 = r6.indexOfChild(r7)
            if (r3 == r6) goto L_0x007d
            com.android.internal.widget.MessagingLinearLayout r6 = r9.mMessageContainer
            android.view.View r7 = r4.getView()
            r6.removeView(r7)
            com.android.internal.widget.MessagingLinearLayout r6 = r9.mMessageContainer
            android.view.View r7 = r4.getView()
            r6.addView((android.view.View) r7, (int) r3)
        L_0x007d:
            int r3 = r3 + 1
        L_0x007f:
            int r0 = r0 + 1
            goto L_0x0005
        L_0x0082:
            if (r1 == 0) goto L_0x009f
            android.view.ViewGroup r0 = r9.mImageContainer
            boolean r0 = r9.removeFromParentIfDifferent(r1, r0)
            if (r0 == 0) goto L_0x009a
            android.view.ViewGroup r0 = r9.mImageContainer
            r0.removeAllViews()
            android.view.ViewGroup r0 = r9.mImageContainer
            android.view.View r2 = r1.getView()
            r0.addView(r2)
        L_0x009a:
            r0 = 1
            r1.setIsolated(r0)
            goto L_0x00a8
        L_0x009f:
            com.android.internal.widget.MessagingImageMessage r0 = r9.mIsolatedMessage
            if (r0 == 0) goto L_0x00a8
            android.view.ViewGroup r0 = r9.mImageContainer
            r0.removeAllViews()
        L_0x00a8:
            r9.mIsolatedMessage = r1
            r9.updateImageContainerVisibility()
            r9.mMessages = r10
            r9.updateMessageColor()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.MessagingGroup.setMessages(java.util.List):void");
    }

    private void updateImageContainerVisibility() {
        this.mImageContainer.setVisibility((this.mIsolatedMessage == null || !this.mImagesAtEnd) ? 8 : 0);
    }

    private boolean removeFromParentIfDifferent(MessagingMessage message, ViewGroup newParent) {
        ViewParent parent = message.getView().getParent();
        if (parent == newParent) {
            return false;
        }
        if (!(parent instanceof ViewGroup)) {
            return true;
        }
        ((ViewGroup) parent).removeView(message.getView());
        return true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.mAddedMessages.isEmpty()) {
            final boolean firstLayout = this.mFirstLayout;
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    Iterator it = MessagingGroup.this.mAddedMessages.iterator();
                    while (it.hasNext()) {
                        MessagingMessage message = (MessagingMessage) it.next();
                        if (message.getView().isShown()) {
                            MessagingPropertyAnimator.fadeIn(message.getView());
                            if (!firstLayout) {
                                MessagingPropertyAnimator.startLocalTranslationFrom(message.getView(), message.getView().getHeight(), MessagingLayout.LINEAR_OUT_SLOW_IN);
                            }
                        }
                    }
                    MessagingGroup.this.mAddedMessages.clear();
                    MessagingGroup.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mFirstLayout = false;
        updateClipRect();
    }

    public int calculateGroupCompatibility(MessagingGroup otherGroup) {
        int i = 0;
        if (!TextUtils.equals(getSenderName(), otherGroup.getSenderName())) {
            return 0;
        }
        int result = 1;
        while (i < this.mMessages.size() && i < otherGroup.mMessages.size() && this.mMessages.get((this.mMessages.size() - 1) - i).sameAs(otherGroup.mMessages.get((otherGroup.mMessages.size() - 1) - i))) {
            result++;
            i++;
        }
        return result;
    }

    public View getSenderView() {
        return this.mSenderName;
    }

    public View getAvatar() {
        return this.mAvatarView;
    }

    public MessagingLinearLayout getMessageContainer() {
        return this.mMessageContainer;
    }

    public MessagingImageMessage getIsolatedMessage() {
        return this.mIsolatedMessage;
    }

    public boolean needsGeneratedAvatar() {
        return this.mNeedsGeneratedAvatar;
    }

    public Person getSender() {
        return this.mSender;
    }

    public void setTransformingImages(boolean transformingImages) {
        this.mTransformingImages = transformingImages;
    }

    public void setDisplayImagesAtEnd(boolean atEnd) {
        if (this.mImagesAtEnd != atEnd) {
            this.mImagesAtEnd = atEnd;
            updateImageContainerVisibility();
        }
    }

    public List<MessagingMessage> getMessages() {
        return this.mMessages;
    }
}
