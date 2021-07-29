package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsIncomingMessageCreationParams;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsMessageSnippet;
import android.telephony.ims.RcsOutgoingMessageCreationParams;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.RcsThreadQueryResultParcelable;

public interface IRcs extends IInterface {
    int addIncomingMessage(int i, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String str) throws RemoteException;

    int addOutgoingMessage(int i, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String str) throws RemoteException;

    void addParticipantToGroupThread(int i, int i2, String str) throws RemoteException;

    int createGroupThread(int[] iArr, String str, Uri uri, String str2) throws RemoteException;

    int createGroupThreadIconChangedEvent(long j, int i, int i2, Uri uri, String str) throws RemoteException;

    int createGroupThreadNameChangedEvent(long j, int i, int i2, String str, String str2) throws RemoteException;

    int createGroupThreadParticipantJoinedEvent(long j, int i, int i2, int i3, String str) throws RemoteException;

    int createGroupThreadParticipantLeftEvent(long j, int i, int i2, int i3, String str) throws RemoteException;

    int createParticipantAliasChangedEvent(long j, int i, String str, String str2) throws RemoteException;

    int createRcs1To1Thread(int i, String str) throws RemoteException;

    int createRcsParticipant(String str, String str2, String str3) throws RemoteException;

    void deleteFileTransfer(int i, String str) throws RemoteException;

    void deleteMessage(int i, boolean z, int i2, boolean z2, String str) throws RemoteException;

    boolean deleteThread(int i, int i2, String str) throws RemoteException;

    long get1To1ThreadFallbackThreadId(int i, String str) throws RemoteException;

    int get1To1ThreadOtherParticipantId(int i, String str) throws RemoteException;

    RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams rcsEventQueryParams, String str) throws RemoteException;

    RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String str) throws RemoteException;

    String getFileTransferContentType(int i, String str) throws RemoteException;

    Uri getFileTransferContentUri(int i, String str) throws RemoteException;

    long getFileTransferFileSize(int i, String str) throws RemoteException;

    int getFileTransferHeight(int i, String str) throws RemoteException;

    long getFileTransferLength(int i, String str) throws RemoteException;

    String getFileTransferPreviewType(int i, String str) throws RemoteException;

    Uri getFileTransferPreviewUri(int i, String str) throws RemoteException;

    String getFileTransferSessionId(int i, String str) throws RemoteException;

    int getFileTransferStatus(int i, String str) throws RemoteException;

    long getFileTransferTransferOffset(int i, String str) throws RemoteException;

    int getFileTransferWidth(int i, String str) throws RemoteException;

    int[] getFileTransfersAttachedToMessage(int i, boolean z, String str) throws RemoteException;

    String getGlobalMessageIdForMessage(int i, boolean z, String str) throws RemoteException;

    Uri getGroupThreadConferenceUri(int i, String str) throws RemoteException;

    Uri getGroupThreadIcon(int i, String str) throws RemoteException;

    String getGroupThreadName(int i, String str) throws RemoteException;

    int getGroupThreadOwner(int i, String str) throws RemoteException;

    double getLatitudeForMessage(int i, boolean z, String str) throws RemoteException;

    double getLongitudeForMessage(int i, boolean z, String str) throws RemoteException;

    long getMessageArrivalTimestamp(int i, boolean z, String str) throws RemoteException;

    long getMessageOriginationTimestamp(int i, boolean z, String str) throws RemoteException;

    int[] getMessageRecipients(int i, String str) throws RemoteException;

    long getMessageSeenTimestamp(int i, boolean z, String str) throws RemoteException;

    RcsMessageSnippet getMessageSnippet(int i, String str) throws RemoteException;

    int getMessageStatus(int i, boolean z, String str) throws RemoteException;

    int getMessageSubId(int i, boolean z, String str) throws RemoteException;

    RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams rcsMessageQueryParams, String str) throws RemoteException;

    RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String str) throws RemoteException;

    long getOutgoingDeliveryDeliveredTimestamp(int i, int i2, String str) throws RemoteException;

    long getOutgoingDeliverySeenTimestamp(int i, int i2, String str) throws RemoteException;

    int getOutgoingDeliveryStatus(int i, int i2, String str) throws RemoteException;

    RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams rcsParticipantQueryParams, String str) throws RemoteException;

    RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String str) throws RemoteException;

    String getRcsParticipantAlias(int i, String str) throws RemoteException;

    String getRcsParticipantCanonicalAddress(int i, String str) throws RemoteException;

    String getRcsParticipantContactId(int i, String str) throws RemoteException;

    RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams rcsThreadQueryParams, String str) throws RemoteException;

    RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken rcsQueryContinuationToken, String str) throws RemoteException;

    int getSenderParticipant(int i, String str) throws RemoteException;

    String getTextForMessage(int i, boolean z, String str) throws RemoteException;

    void removeParticipantFromGroupThread(int i, int i2, String str) throws RemoteException;

    void set1To1ThreadFallbackThreadId(int i, long j, String str) throws RemoteException;

    void setFileTransferContentType(int i, String str, String str2) throws RemoteException;

    void setFileTransferContentUri(int i, Uri uri, String str) throws RemoteException;

    void setFileTransferFileSize(int i, long j, String str) throws RemoteException;

    void setFileTransferHeight(int i, int i2, String str) throws RemoteException;

    void setFileTransferLength(int i, long j, String str) throws RemoteException;

    void setFileTransferPreviewType(int i, String str, String str2) throws RemoteException;

    void setFileTransferPreviewUri(int i, Uri uri, String str) throws RemoteException;

    void setFileTransferSessionId(int i, String str, String str2) throws RemoteException;

    void setFileTransferStatus(int i, int i2, String str) throws RemoteException;

    void setFileTransferTransferOffset(int i, long j, String str) throws RemoteException;

    void setFileTransferWidth(int i, int i2, String str) throws RemoteException;

    void setGlobalMessageIdForMessage(int i, boolean z, String str, String str2) throws RemoteException;

    void setGroupThreadConferenceUri(int i, Uri uri, String str) throws RemoteException;

    void setGroupThreadIcon(int i, Uri uri, String str) throws RemoteException;

    void setGroupThreadName(int i, String str, String str2) throws RemoteException;

    void setGroupThreadOwner(int i, int i2, String str) throws RemoteException;

    void setLatitudeForMessage(int i, boolean z, double d, String str) throws RemoteException;

    void setLongitudeForMessage(int i, boolean z, double d, String str) throws RemoteException;

    void setMessageArrivalTimestamp(int i, boolean z, long j, String str) throws RemoteException;

    void setMessageOriginationTimestamp(int i, boolean z, long j, String str) throws RemoteException;

    void setMessageSeenTimestamp(int i, boolean z, long j, String str) throws RemoteException;

    void setMessageStatus(int i, boolean z, int i2, String str) throws RemoteException;

    void setMessageSubId(int i, boolean z, int i2, String str) throws RemoteException;

    void setOutgoingDeliveryDeliveredTimestamp(int i, int i2, long j, String str) throws RemoteException;

    void setOutgoingDeliverySeenTimestamp(int i, int i2, long j, String str) throws RemoteException;

    void setOutgoingDeliveryStatus(int i, int i2, int i3, String str) throws RemoteException;

    void setRcsParticipantAlias(int i, String str, String str2) throws RemoteException;

    void setRcsParticipantContactId(int i, String str, String str2) throws RemoteException;

    void setTextForMessage(int i, boolean z, String str, String str2) throws RemoteException;

    int storeFileTransfer(int i, boolean z, RcsFileTransferCreationParams rcsFileTransferCreationParams, String str) throws RemoteException;

    public static class Default implements IRcs {
        public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams queryParams, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams queryParams, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams queryParams, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams queryParams, String callingPackage) throws RemoteException {
            return null;
        }

        public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean deleteThread(int threadId, int threadType, String callingPackage) throws RemoteException {
            return false;
        }

        public int createRcs1To1Thread(int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int createGroupThread(int[] participantIds, String groupName, Uri groupIcon, String callingPackage) throws RemoteException {
            return 0;
        }

        public int addIncomingMessage(int rcsThreadId, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String callingPackage) throws RemoteException {
            return 0;
        }

        public int addOutgoingMessage(int rcsThreadId, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String callingPackage) throws RemoteException {
            return 0;
        }

        public void deleteMessage(int rcsMessageId, boolean isIncoming, int rcsThreadId, boolean isGroup, String callingPackage) throws RemoteException {
        }

        public RcsMessageSnippet getMessageSnippet(int rcsThreadId, String callingPackage) throws RemoteException {
            return null;
        }

        public void set1To1ThreadFallbackThreadId(int rcsThreadId, long fallbackId, String callingPackage) throws RemoteException {
        }

        public long get1To1ThreadFallbackThreadId(int rcsThreadId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int get1To1ThreadOtherParticipantId(int rcsThreadId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setGroupThreadName(int rcsThreadId, String groupName, String callingPackage) throws RemoteException {
        }

        public String getGroupThreadName(int rcsThreadId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setGroupThreadIcon(int rcsThreadId, Uri groupIcon, String callingPackage) throws RemoteException {
        }

        public Uri getGroupThreadIcon(int rcsThreadId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setGroupThreadOwner(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
        }

        public int getGroupThreadOwner(int rcsThreadId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setGroupThreadConferenceUri(int rcsThreadId, Uri conferenceUri, String callingPackage) throws RemoteException {
        }

        public Uri getGroupThreadConferenceUri(int rcsThreadId, String callingPackage) throws RemoteException {
            return null;
        }

        public void addParticipantToGroupThread(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
        }

        public void removeParticipantFromGroupThread(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
        }

        public int createRcsParticipant(String canonicalAddress, String alias, String callingPackage) throws RemoteException {
            return 0;
        }

        public String getRcsParticipantCanonicalAddress(int participantId, String callingPackage) throws RemoteException {
            return null;
        }

        public String getRcsParticipantAlias(int participantId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setRcsParticipantAlias(int id, String alias, String callingPackage) throws RemoteException {
        }

        public String getRcsParticipantContactId(int participantId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setRcsParticipantContactId(int participantId, String contactId, String callingPackage) throws RemoteException {
        }

        public void setMessageSubId(int messageId, boolean isIncoming, int subId, String callingPackage) throws RemoteException {
        }

        public int getMessageSubId(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setMessageStatus(int messageId, boolean isIncoming, int status, String callingPackage) throws RemoteException {
        }

        public int getMessageStatus(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setMessageOriginationTimestamp(int messageId, boolean isIncoming, long originationTimestamp, String callingPackage) throws RemoteException {
        }

        public long getMessageOriginationTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setGlobalMessageIdForMessage(int messageId, boolean isIncoming, String globalId, String callingPackage) throws RemoteException {
        }

        public String getGlobalMessageIdForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return null;
        }

        public void setMessageArrivalTimestamp(int messageId, boolean isIncoming, long arrivalTimestamp, String callingPackage) throws RemoteException {
        }

        public long getMessageArrivalTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setMessageSeenTimestamp(int messageId, boolean isIncoming, long seenTimestamp, String callingPackage) throws RemoteException {
        }

        public long getMessageSeenTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setTextForMessage(int messageId, boolean isIncoming, String text, String callingPackage) throws RemoteException {
        }

        public String getTextForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return null;
        }

        public void setLatitudeForMessage(int messageId, boolean isIncoming, double latitude, String callingPackage) throws RemoteException {
        }

        public double getLatitudeForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0.0d;
        }

        public void setLongitudeForMessage(int messageId, boolean isIncoming, double longitude, String callingPackage) throws RemoteException {
        }

        public double getLongitudeForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return 0.0d;
        }

        public int[] getFileTransfersAttachedToMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
            return null;
        }

        public int getSenderParticipant(int messageId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int[] getMessageRecipients(int messageId, String callingPackage) throws RemoteException {
            return null;
        }

        public long getOutgoingDeliveryDeliveredTimestamp(int messageId, int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setOutgoingDeliveryDeliveredTimestamp(int messageId, int participantId, long deliveredTimestamp, String callingPackage) throws RemoteException {
        }

        public long getOutgoingDeliverySeenTimestamp(int messageId, int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setOutgoingDeliverySeenTimestamp(int messageId, int participantId, long seenTimestamp, String callingPackage) throws RemoteException {
        }

        public int getOutgoingDeliveryStatus(int messageId, int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setOutgoingDeliveryStatus(int messageId, int participantId, int status, String callingPackage) throws RemoteException {
        }

        public int storeFileTransfer(int messageId, boolean isIncoming, RcsFileTransferCreationParams fileTransferCreationParams, String callingPackage) throws RemoteException {
            return 0;
        }

        public void deleteFileTransfer(int partId, String callingPackage) throws RemoteException {
        }

        public void setFileTransferSessionId(int partId, String sessionId, String callingPackage) throws RemoteException {
        }

        public String getFileTransferSessionId(int partId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setFileTransferContentUri(int partId, Uri contentUri, String callingPackage) throws RemoteException {
        }

        public Uri getFileTransferContentUri(int partId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setFileTransferContentType(int partId, String contentType, String callingPackage) throws RemoteException {
        }

        public String getFileTransferContentType(int partId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setFileTransferFileSize(int partId, long fileSize, String callingPackage) throws RemoteException {
        }

        public long getFileTransferFileSize(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferTransferOffset(int partId, long transferOffset, String callingPackage) throws RemoteException {
        }

        public long getFileTransferTransferOffset(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferStatus(int partId, int transferStatus, String callingPackage) throws RemoteException {
        }

        public int getFileTransferStatus(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferWidth(int partId, int width, String callingPackage) throws RemoteException {
        }

        public int getFileTransferWidth(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferHeight(int partId, int height, String callingPackage) throws RemoteException {
        }

        public int getFileTransferHeight(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferLength(int partId, long length, String callingPackage) throws RemoteException {
        }

        public long getFileTransferLength(int partId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void setFileTransferPreviewUri(int partId, Uri uri, String callingPackage) throws RemoteException {
        }

        public Uri getFileTransferPreviewUri(int partId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setFileTransferPreviewType(int partId, String type, String callingPackage) throws RemoteException {
        }

        public String getFileTransferPreviewType(int partId, String callingPackage) throws RemoteException {
            return null;
        }

        public int createGroupThreadNameChangedEvent(long timestamp, int threadId, int originationParticipantId, String newName, String callingPackage) throws RemoteException {
            return 0;
        }

        public int createGroupThreadIconChangedEvent(long timestamp, int threadId, int originationParticipantId, Uri newIcon, String callingPackage) throws RemoteException {
            return 0;
        }

        public int createGroupThreadParticipantJoinedEvent(long timestamp, int threadId, int originationParticipantId, int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int createGroupThreadParticipantLeftEvent(long timestamp, int threadId, int originationParticipantId, int participantId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int createParticipantAliasChangedEvent(long timestamp, int participantId, String newAlias, String callingPackage) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRcs {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcs";
        static final int TRANSACTION_addIncomingMessage = 12;
        static final int TRANSACTION_addOutgoingMessage = 13;
        static final int TRANSACTION_addParticipantToGroupThread = 27;
        static final int TRANSACTION_createGroupThread = 11;
        static final int TRANSACTION_createGroupThreadIconChangedEvent = 87;
        static final int TRANSACTION_createGroupThreadNameChangedEvent = 86;
        static final int TRANSACTION_createGroupThreadParticipantJoinedEvent = 88;
        static final int TRANSACTION_createGroupThreadParticipantLeftEvent = 89;
        static final int TRANSACTION_createParticipantAliasChangedEvent = 90;
        static final int TRANSACTION_createRcs1To1Thread = 10;
        static final int TRANSACTION_createRcsParticipant = 29;
        static final int TRANSACTION_deleteFileTransfer = 63;
        static final int TRANSACTION_deleteMessage = 14;
        static final int TRANSACTION_deleteThread = 9;
        static final int TRANSACTION_get1To1ThreadFallbackThreadId = 17;
        static final int TRANSACTION_get1To1ThreadOtherParticipantId = 18;
        static final int TRANSACTION_getEvents = 7;
        static final int TRANSACTION_getEventsWithToken = 8;
        static final int TRANSACTION_getFileTransferContentType = 69;
        static final int TRANSACTION_getFileTransferContentUri = 67;
        static final int TRANSACTION_getFileTransferFileSize = 71;
        static final int TRANSACTION_getFileTransferHeight = 79;
        static final int TRANSACTION_getFileTransferLength = 81;
        static final int TRANSACTION_getFileTransferPreviewType = 85;
        static final int TRANSACTION_getFileTransferPreviewUri = 83;
        static final int TRANSACTION_getFileTransferSessionId = 65;
        static final int TRANSACTION_getFileTransferStatus = 75;
        static final int TRANSACTION_getFileTransferTransferOffset = 73;
        static final int TRANSACTION_getFileTransferWidth = 77;
        static final int TRANSACTION_getFileTransfersAttachedToMessage = 53;
        static final int TRANSACTION_getGlobalMessageIdForMessage = 42;
        static final int TRANSACTION_getGroupThreadConferenceUri = 26;
        static final int TRANSACTION_getGroupThreadIcon = 22;
        static final int TRANSACTION_getGroupThreadName = 20;
        static final int TRANSACTION_getGroupThreadOwner = 24;
        static final int TRANSACTION_getLatitudeForMessage = 50;
        static final int TRANSACTION_getLongitudeForMessage = 52;
        static final int TRANSACTION_getMessageArrivalTimestamp = 44;
        static final int TRANSACTION_getMessageOriginationTimestamp = 40;
        static final int TRANSACTION_getMessageRecipients = 55;
        static final int TRANSACTION_getMessageSeenTimestamp = 46;
        static final int TRANSACTION_getMessageSnippet = 15;
        static final int TRANSACTION_getMessageStatus = 38;
        static final int TRANSACTION_getMessageSubId = 36;
        static final int TRANSACTION_getMessages = 5;
        static final int TRANSACTION_getMessagesWithToken = 6;
        static final int TRANSACTION_getOutgoingDeliveryDeliveredTimestamp = 56;
        static final int TRANSACTION_getOutgoingDeliverySeenTimestamp = 58;
        static final int TRANSACTION_getOutgoingDeliveryStatus = 60;
        static final int TRANSACTION_getParticipants = 3;
        static final int TRANSACTION_getParticipantsWithToken = 4;
        static final int TRANSACTION_getRcsParticipantAlias = 31;
        static final int TRANSACTION_getRcsParticipantCanonicalAddress = 30;
        static final int TRANSACTION_getRcsParticipantContactId = 33;
        static final int TRANSACTION_getRcsThreads = 1;
        static final int TRANSACTION_getRcsThreadsWithToken = 2;
        static final int TRANSACTION_getSenderParticipant = 54;
        static final int TRANSACTION_getTextForMessage = 48;
        static final int TRANSACTION_removeParticipantFromGroupThread = 28;
        static final int TRANSACTION_set1To1ThreadFallbackThreadId = 16;
        static final int TRANSACTION_setFileTransferContentType = 68;
        static final int TRANSACTION_setFileTransferContentUri = 66;
        static final int TRANSACTION_setFileTransferFileSize = 70;
        static final int TRANSACTION_setFileTransferHeight = 78;
        static final int TRANSACTION_setFileTransferLength = 80;
        static final int TRANSACTION_setFileTransferPreviewType = 84;
        static final int TRANSACTION_setFileTransferPreviewUri = 82;
        static final int TRANSACTION_setFileTransferSessionId = 64;
        static final int TRANSACTION_setFileTransferStatus = 74;
        static final int TRANSACTION_setFileTransferTransferOffset = 72;
        static final int TRANSACTION_setFileTransferWidth = 76;
        static final int TRANSACTION_setGlobalMessageIdForMessage = 41;
        static final int TRANSACTION_setGroupThreadConferenceUri = 25;
        static final int TRANSACTION_setGroupThreadIcon = 21;
        static final int TRANSACTION_setGroupThreadName = 19;
        static final int TRANSACTION_setGroupThreadOwner = 23;
        static final int TRANSACTION_setLatitudeForMessage = 49;
        static final int TRANSACTION_setLongitudeForMessage = 51;
        static final int TRANSACTION_setMessageArrivalTimestamp = 43;
        static final int TRANSACTION_setMessageOriginationTimestamp = 39;
        static final int TRANSACTION_setMessageSeenTimestamp = 45;
        static final int TRANSACTION_setMessageStatus = 37;
        static final int TRANSACTION_setMessageSubId = 35;
        static final int TRANSACTION_setOutgoingDeliveryDeliveredTimestamp = 57;
        static final int TRANSACTION_setOutgoingDeliverySeenTimestamp = 59;
        static final int TRANSACTION_setOutgoingDeliveryStatus = 61;
        static final int TRANSACTION_setRcsParticipantAlias = 32;
        static final int TRANSACTION_setRcsParticipantContactId = 34;
        static final int TRANSACTION_setTextForMessage = 47;
        static final int TRANSACTION_storeFileTransfer = 62;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRcs asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRcs)) {
                return new Proxy(obj);
            }
            return (IRcs) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getRcsThreads";
                case 2:
                    return "getRcsThreadsWithToken";
                case 3:
                    return "getParticipants";
                case 4:
                    return "getParticipantsWithToken";
                case 5:
                    return "getMessages";
                case 6:
                    return "getMessagesWithToken";
                case 7:
                    return "getEvents";
                case 8:
                    return "getEventsWithToken";
                case 9:
                    return "deleteThread";
                case 10:
                    return "createRcs1To1Thread";
                case 11:
                    return "createGroupThread";
                case 12:
                    return "addIncomingMessage";
                case 13:
                    return "addOutgoingMessage";
                case 14:
                    return "deleteMessage";
                case 15:
                    return "getMessageSnippet";
                case 16:
                    return "set1To1ThreadFallbackThreadId";
                case 17:
                    return "get1To1ThreadFallbackThreadId";
                case 18:
                    return "get1To1ThreadOtherParticipantId";
                case 19:
                    return "setGroupThreadName";
                case 20:
                    return "getGroupThreadName";
                case 21:
                    return "setGroupThreadIcon";
                case 22:
                    return "getGroupThreadIcon";
                case 23:
                    return "setGroupThreadOwner";
                case 24:
                    return "getGroupThreadOwner";
                case 25:
                    return "setGroupThreadConferenceUri";
                case 26:
                    return "getGroupThreadConferenceUri";
                case 27:
                    return "addParticipantToGroupThread";
                case 28:
                    return "removeParticipantFromGroupThread";
                case 29:
                    return "createRcsParticipant";
                case 30:
                    return "getRcsParticipantCanonicalAddress";
                case 31:
                    return "getRcsParticipantAlias";
                case 32:
                    return "setRcsParticipantAlias";
                case 33:
                    return "getRcsParticipantContactId";
                case 34:
                    return "setRcsParticipantContactId";
                case 35:
                    return "setMessageSubId";
                case 36:
                    return "getMessageSubId";
                case 37:
                    return "setMessageStatus";
                case 38:
                    return "getMessageStatus";
                case 39:
                    return "setMessageOriginationTimestamp";
                case 40:
                    return "getMessageOriginationTimestamp";
                case 41:
                    return "setGlobalMessageIdForMessage";
                case 42:
                    return "getGlobalMessageIdForMessage";
                case 43:
                    return "setMessageArrivalTimestamp";
                case 44:
                    return "getMessageArrivalTimestamp";
                case 45:
                    return "setMessageSeenTimestamp";
                case 46:
                    return "getMessageSeenTimestamp";
                case 47:
                    return "setTextForMessage";
                case 48:
                    return "getTextForMessage";
                case 49:
                    return "setLatitudeForMessage";
                case 50:
                    return "getLatitudeForMessage";
                case 51:
                    return "setLongitudeForMessage";
                case 52:
                    return "getLongitudeForMessage";
                case 53:
                    return "getFileTransfersAttachedToMessage";
                case 54:
                    return "getSenderParticipant";
                case 55:
                    return "getMessageRecipients";
                case 56:
                    return "getOutgoingDeliveryDeliveredTimestamp";
                case 57:
                    return "setOutgoingDeliveryDeliveredTimestamp";
                case 58:
                    return "getOutgoingDeliverySeenTimestamp";
                case 59:
                    return "setOutgoingDeliverySeenTimestamp";
                case 60:
                    return "getOutgoingDeliveryStatus";
                case 61:
                    return "setOutgoingDeliveryStatus";
                case 62:
                    return "storeFileTransfer";
                case 63:
                    return "deleteFileTransfer";
                case 64:
                    return "setFileTransferSessionId";
                case 65:
                    return "getFileTransferSessionId";
                case 66:
                    return "setFileTransferContentUri";
                case 67:
                    return "getFileTransferContentUri";
                case 68:
                    return "setFileTransferContentType";
                case 69:
                    return "getFileTransferContentType";
                case 70:
                    return "setFileTransferFileSize";
                case 71:
                    return "getFileTransferFileSize";
                case 72:
                    return "setFileTransferTransferOffset";
                case 73:
                    return "getFileTransferTransferOffset";
                case 74:
                    return "setFileTransferStatus";
                case 75:
                    return "getFileTransferStatus";
                case 76:
                    return "setFileTransferWidth";
                case 77:
                    return "getFileTransferWidth";
                case 78:
                    return "setFileTransferHeight";
                case 79:
                    return "getFileTransferHeight";
                case 80:
                    return "setFileTransferLength";
                case 81:
                    return "getFileTransferLength";
                case 82:
                    return "setFileTransferPreviewUri";
                case 83:
                    return "getFileTransferPreviewUri";
                case 84:
                    return "setFileTransferPreviewType";
                case 85:
                    return "getFileTransferPreviewType";
                case 86:
                    return "createGroupThreadNameChangedEvent";
                case 87:
                    return "createGroupThreadIconChangedEvent";
                case 88:
                    return "createGroupThreadParticipantJoinedEvent";
                case 89:
                    return "createGroupThreadParticipantLeftEvent";
                case 90:
                    return "createParticipantAliasChangedEvent";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.telephony.ims.RcsThreadQueryParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: android.telephony.ims.RcsQueryContinuationToken} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.telephony.ims.RcsParticipantQueryParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.telephony.ims.RcsQueryContinuationToken} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.telephony.ims.RcsMessageQueryParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: android.telephony.ims.RcsQueryContinuationToken} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: android.telephony.ims.RcsEventQueryParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.telephony.ims.RcsQueryContinuationToken} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: android.telephony.ims.RcsIncomingMessageCreationParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: android.telephony.ims.RcsOutgoingMessageCreationParams} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v109, resolved type: android.telephony.ims.RcsFileTransferCreationParams} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v36, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v57, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v64, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v116, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v135, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v144 */
        /* JADX WARNING: type inference failed for: r0v157 */
        /* JADX WARNING: type inference failed for: r0v158 */
        /* JADX WARNING: type inference failed for: r0v159 */
        /* JADX WARNING: type inference failed for: r0v160 */
        /* JADX WARNING: type inference failed for: r0v161 */
        /* JADX WARNING: type inference failed for: r0v162 */
        /* JADX WARNING: type inference failed for: r0v163 */
        /* JADX WARNING: type inference failed for: r0v164 */
        /* JADX WARNING: type inference failed for: r0v165 */
        /* JADX WARNING: type inference failed for: r0v166 */
        /* JADX WARNING: type inference failed for: r0v167 */
        /* JADX WARNING: type inference failed for: r0v168 */
        /* JADX WARNING: type inference failed for: r0v169 */
        /* JADX WARNING: type inference failed for: r0v170 */
        /* JADX WARNING: type inference failed for: r0v171 */
        /* JADX WARNING: type inference failed for: r0v172 */
        /* JADX WARNING: type inference failed for: r0v173 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r7 = r19
                r8 = r20
                r9 = r21
                r10 = r22
                java.lang.String r11 = "android.telephony.ims.aidl.IRcs"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x0a90
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0a65;
                    case 2: goto L_0x0a3a;
                    case 3: goto L_0x0a0f;
                    case 4: goto L_0x09e4;
                    case 5: goto L_0x09b9;
                    case 6: goto L_0x098e;
                    case 7: goto L_0x0963;
                    case 8: goto L_0x0938;
                    case 9: goto L_0x091e;
                    case 10: goto L_0x0908;
                    case 11: goto L_0x08de;
                    case 12: goto L_0x08b8;
                    case 13: goto L_0x0892;
                    case 14: goto L_0x0865;
                    case 15: goto L_0x0846;
                    case 16: goto L_0x0830;
                    case 17: goto L_0x081a;
                    case 18: goto L_0x0804;
                    case 19: goto L_0x07ee;
                    case 20: goto L_0x07d8;
                    case 21: goto L_0x07b6;
                    case 22: goto L_0x0797;
                    case 23: goto L_0x0781;
                    case 24: goto L_0x076b;
                    case 25: goto L_0x0749;
                    case 26: goto L_0x072a;
                    case 27: goto L_0x0714;
                    case 28: goto L_0x06fe;
                    case 29: goto L_0x06e4;
                    case 30: goto L_0x06ce;
                    case 31: goto L_0x06b8;
                    case 32: goto L_0x06a2;
                    case 33: goto L_0x068c;
                    case 34: goto L_0x0676;
                    case 35: goto L_0x0658;
                    case 36: goto L_0x063a;
                    case 37: goto L_0x061c;
                    case 38: goto L_0x05fe;
                    case 39: goto L_0x05da;
                    case 40: goto L_0x05bc;
                    case 41: goto L_0x059e;
                    case 42: goto L_0x0580;
                    case 43: goto L_0x055c;
                    case 44: goto L_0x053e;
                    case 45: goto L_0x051a;
                    case 46: goto L_0x04fc;
                    case 47: goto L_0x04de;
                    case 48: goto L_0x04c0;
                    case 49: goto L_0x049c;
                    case 50: goto L_0x047e;
                    case 51: goto L_0x045a;
                    case 52: goto L_0x043c;
                    case 53: goto L_0x041e;
                    case 54: goto L_0x0408;
                    case 55: goto L_0x03f2;
                    case 56: goto L_0x03d8;
                    case 57: goto L_0x03b7;
                    case 58: goto L_0x039d;
                    case 59: goto L_0x037c;
                    case 60: goto L_0x0362;
                    case 61: goto L_0x0348;
                    case 62: goto L_0x031a;
                    case 63: goto L_0x0308;
                    case 64: goto L_0x02f2;
                    case 65: goto L_0x02dc;
                    case 66: goto L_0x02ba;
                    case 67: goto L_0x029b;
                    case 68: goto L_0x0285;
                    case 69: goto L_0x026f;
                    case 70: goto L_0x0259;
                    case 71: goto L_0x0243;
                    case 72: goto L_0x022d;
                    case 73: goto L_0x0217;
                    case 74: goto L_0x0201;
                    case 75: goto L_0x01eb;
                    case 76: goto L_0x01d5;
                    case 77: goto L_0x01bf;
                    case 78: goto L_0x01a9;
                    case 79: goto L_0x0193;
                    case 80: goto L_0x017d;
                    case 81: goto L_0x0167;
                    case 82: goto L_0x0145;
                    case 83: goto L_0x0126;
                    case 84: goto L_0x0110;
                    case 85: goto L_0x00fa;
                    case 86: goto L_0x00ce;
                    case 87: goto L_0x0097;
                    case 88: goto L_0x006b;
                    case 89: goto L_0x003f;
                    case 90: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                long r13 = r21.readLong()
                int r6 = r21.readInt()
                java.lang.String r15 = r21.readString()
                java.lang.String r16 = r21.readString()
                r0 = r19
                r1 = r13
                r3 = r6
                r4 = r15
                r5 = r16
                int r0 = r0.createParticipantAliasChangedEvent(r1, r3, r4, r5)
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x003f:
                r9.enforceInterface(r11)
                long r13 = r21.readLong()
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                int r17 = r21.readInt()
                java.lang.String r18 = r21.readString()
                r0 = r19
                r1 = r13
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                int r0 = r0.createGroupThreadParticipantLeftEvent(r1, r3, r4, r5, r6)
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x006b:
                r9.enforceInterface(r11)
                long r13 = r21.readLong()
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                int r17 = r21.readInt()
                java.lang.String r18 = r21.readString()
                r0 = r19
                r1 = r13
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                int r0 = r0.createGroupThreadParticipantJoinedEvent(r1, r3, r4, r5, r6)
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0097:
                r9.enforceInterface(r11)
                long r13 = r21.readLong()
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00b6
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
            L_0x00b4:
                r5 = r0
                goto L_0x00b7
            L_0x00b6:
                goto L_0x00b4
            L_0x00b7:
                java.lang.String r17 = r21.readString()
                r0 = r19
                r1 = r13
                r3 = r15
                r4 = r16
                r6 = r17
                int r0 = r0.createGroupThreadIconChangedEvent(r1, r3, r4, r5, r6)
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x00ce:
                r9.enforceInterface(r11)
                long r13 = r21.readLong()
                int r15 = r21.readInt()
                int r16 = r21.readInt()
                java.lang.String r17 = r21.readString()
                java.lang.String r18 = r21.readString()
                r0 = r19
                r1 = r13
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                int r0 = r0.createGroupThreadNameChangedEvent(r1, r3, r4, r5, r6)
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x00fa:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getFileTransferPreviewType(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x0110:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferPreviewType(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0126:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.net.Uri r3 = r7.getFileTransferPreviewUri(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0141
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0144
            L_0x0141:
                r10.writeInt(r1)
            L_0x0144:
                return r12
            L_0x0145:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x015b
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x015c
            L_0x015b:
            L_0x015c:
                java.lang.String r2 = r21.readString()
                r7.setFileTransferPreviewUri(r1, r0, r2)
                r22.writeNoException()
                return r12
            L_0x0167:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                long r2 = r7.getFileTransferLength(r0, r1)
                r22.writeNoException()
                r10.writeLong(r2)
                return r12
            L_0x017d:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                long r1 = r21.readLong()
                java.lang.String r3 = r21.readString()
                r7.setFileTransferLength(r0, r1, r3)
                r22.writeNoException()
                return r12
            L_0x0193:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.getFileTransferHeight(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x01a9:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferHeight(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x01bf:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.getFileTransferWidth(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x01d5:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferWidth(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x01eb:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.getFileTransferStatus(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0201:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferStatus(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0217:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                long r2 = r7.getFileTransferTransferOffset(r0, r1)
                r22.writeNoException()
                r10.writeLong(r2)
                return r12
            L_0x022d:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                long r1 = r21.readLong()
                java.lang.String r3 = r21.readString()
                r7.setFileTransferTransferOffset(r0, r1, r3)
                r22.writeNoException()
                return r12
            L_0x0243:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                long r2 = r7.getFileTransferFileSize(r0, r1)
                r22.writeNoException()
                r10.writeLong(r2)
                return r12
            L_0x0259:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                long r1 = r21.readLong()
                java.lang.String r3 = r21.readString()
                r7.setFileTransferFileSize(r0, r1, r3)
                r22.writeNoException()
                return r12
            L_0x026f:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getFileTransferContentType(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x0285:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferContentType(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x029b:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.net.Uri r3 = r7.getFileTransferContentUri(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x02b6
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x02b9
            L_0x02b6:
                r10.writeInt(r1)
            L_0x02b9:
                return r12
            L_0x02ba:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x02d0
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x02d1
            L_0x02d0:
            L_0x02d1:
                java.lang.String r2 = r21.readString()
                r7.setFileTransferContentUri(r1, r0, r2)
                r22.writeNoException()
                return r12
            L_0x02dc:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getFileTransferSessionId(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x02f2:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setFileTransferSessionId(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0308:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                r7.deleteFileTransfer(r0, r1)
                r22.writeNoException()
                return r12
            L_0x031a:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0329
                r1 = r12
            L_0x0329:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0338
                android.os.Parcelable$Creator<android.telephony.ims.RcsFileTransferCreationParams> r0 = android.telephony.ims.RcsFileTransferCreationParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsFileTransferCreationParams r0 = (android.telephony.ims.RcsFileTransferCreationParams) r0
                goto L_0x0339
            L_0x0338:
            L_0x0339:
                java.lang.String r3 = r21.readString()
                int r4 = r7.storeFileTransfer(r2, r1, r0, r3)
                r22.writeNoException()
                r10.writeInt(r4)
                return r12
            L_0x0348:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                java.lang.String r3 = r21.readString()
                r7.setOutgoingDeliveryStatus(r0, r1, r2, r3)
                r22.writeNoException()
                return r12
            L_0x0362:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                int r3 = r7.getOutgoingDeliveryStatus(r0, r1, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x037c:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r13 = r21.readInt()
                long r14 = r21.readLong()
                java.lang.String r16 = r21.readString()
                r0 = r19
                r1 = r6
                r2 = r13
                r3 = r14
                r5 = r16
                r0.setOutgoingDeliverySeenTimestamp(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x039d:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                long r3 = r7.getOutgoingDeliverySeenTimestamp(r0, r1, r2)
                r22.writeNoException()
                r10.writeLong(r3)
                return r12
            L_0x03b7:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r13 = r21.readInt()
                long r14 = r21.readLong()
                java.lang.String r16 = r21.readString()
                r0 = r19
                r1 = r6
                r2 = r13
                r3 = r14
                r5 = r16
                r0.setOutgoingDeliveryDeliveredTimestamp(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x03d8:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                long r3 = r7.getOutgoingDeliveryDeliveredTimestamp(r0, r1, r2)
                r22.writeNoException()
                r10.writeLong(r3)
                return r12
            L_0x03f2:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int[] r2 = r7.getMessageRecipients(r0, r1)
                r22.writeNoException()
                r10.writeIntArray(r2)
                return r12
            L_0x0408:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.getSenderParticipant(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x041e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x042d
                r1 = r12
            L_0x042d:
                java.lang.String r2 = r21.readString()
                int[] r3 = r7.getFileTransfersAttachedToMessage(r0, r1, r2)
                r22.writeNoException()
                r10.writeIntArray(r3)
                return r12
            L_0x043c:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x044b
                r1 = r12
            L_0x044b:
                java.lang.String r2 = r21.readString()
                double r3 = r7.getLongitudeForMessage(r0, r1, r2)
                r22.writeNoException()
                r10.writeDouble(r3)
                return r12
            L_0x045a:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0469
                r2 = r12
                goto L_0x046a
            L_0x0469:
                r2 = r1
            L_0x046a:
                double r13 = r21.readDouble()
                java.lang.String r15 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r15
                r0.setLongitudeForMessage(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x047e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x048d
                r1 = r12
            L_0x048d:
                java.lang.String r2 = r21.readString()
                double r3 = r7.getLatitudeForMessage(r0, r1, r2)
                r22.writeNoException()
                r10.writeDouble(r3)
                return r12
            L_0x049c:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04ab
                r2 = r12
                goto L_0x04ac
            L_0x04ab:
                r2 = r1
            L_0x04ac:
                double r13 = r21.readDouble()
                java.lang.String r15 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r15
                r0.setLatitudeForMessage(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x04c0:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x04cf
                r1 = r12
            L_0x04cf:
                java.lang.String r2 = r21.readString()
                java.lang.String r3 = r7.getTextForMessage(r0, r1, r2)
                r22.writeNoException()
                r10.writeString(r3)
                return r12
            L_0x04de:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x04ed
                r1 = r12
            L_0x04ed:
                java.lang.String r2 = r21.readString()
                java.lang.String r3 = r21.readString()
                r7.setTextForMessage(r0, r1, r2, r3)
                r22.writeNoException()
                return r12
            L_0x04fc:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x050b
                r1 = r12
            L_0x050b:
                java.lang.String r2 = r21.readString()
                long r3 = r7.getMessageSeenTimestamp(r0, r1, r2)
                r22.writeNoException()
                r10.writeLong(r3)
                return r12
            L_0x051a:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0529
                r2 = r12
                goto L_0x052a
            L_0x0529:
                r2 = r1
            L_0x052a:
                long r13 = r21.readLong()
                java.lang.String r15 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r15
                r0.setMessageSeenTimestamp(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x053e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x054d
                r1 = r12
            L_0x054d:
                java.lang.String r2 = r21.readString()
                long r3 = r7.getMessageArrivalTimestamp(r0, r1, r2)
                r22.writeNoException()
                r10.writeLong(r3)
                return r12
            L_0x055c:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x056b
                r2 = r12
                goto L_0x056c
            L_0x056b:
                r2 = r1
            L_0x056c:
                long r13 = r21.readLong()
                java.lang.String r15 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r15
                r0.setMessageArrivalTimestamp(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x0580:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x058f
                r1 = r12
            L_0x058f:
                java.lang.String r2 = r21.readString()
                java.lang.String r3 = r7.getGlobalMessageIdForMessage(r0, r1, r2)
                r22.writeNoException()
                r10.writeString(r3)
                return r12
            L_0x059e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x05ad
                r1 = r12
            L_0x05ad:
                java.lang.String r2 = r21.readString()
                java.lang.String r3 = r21.readString()
                r7.setGlobalMessageIdForMessage(r0, r1, r2, r3)
                r22.writeNoException()
                return r12
            L_0x05bc:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x05cb
                r1 = r12
            L_0x05cb:
                java.lang.String r2 = r21.readString()
                long r3 = r7.getMessageOriginationTimestamp(r0, r1, r2)
                r22.writeNoException()
                r10.writeLong(r3)
                return r12
            L_0x05da:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x05e9
                r2 = r12
                goto L_0x05ea
            L_0x05e9:
                r2 = r1
            L_0x05ea:
                long r13 = r21.readLong()
                java.lang.String r15 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r15
                r0.setMessageOriginationTimestamp(r1, r2, r3, r5)
                r22.writeNoException()
                return r12
            L_0x05fe:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x060d
                r1 = r12
            L_0x060d:
                java.lang.String r2 = r21.readString()
                int r3 = r7.getMessageStatus(r0, r1, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x061c:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x062b
                r1 = r12
            L_0x062b:
                int r2 = r21.readInt()
                java.lang.String r3 = r21.readString()
                r7.setMessageStatus(r0, r1, r2, r3)
                r22.writeNoException()
                return r12
            L_0x063a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0649
                r1 = r12
            L_0x0649:
                java.lang.String r2 = r21.readString()
                int r3 = r7.getMessageSubId(r0, r1, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x0658:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0667
                r1 = r12
            L_0x0667:
                int r2 = r21.readInt()
                java.lang.String r3 = r21.readString()
                r7.setMessageSubId(r0, r1, r2, r3)
                r22.writeNoException()
                return r12
            L_0x0676:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setRcsParticipantContactId(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x068c:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getRcsParticipantContactId(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x06a2:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setRcsParticipantAlias(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x06b8:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getRcsParticipantAlias(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x06ce:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getRcsParticipantCanonicalAddress(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x06e4:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                int r3 = r7.createRcsParticipant(r0, r1, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x06fe:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.removeParticipantFromGroupThread(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0714:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.addParticipantToGroupThread(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x072a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.net.Uri r3 = r7.getGroupThreadConferenceUri(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0745
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0748
            L_0x0745:
                r10.writeInt(r1)
            L_0x0748:
                return r12
            L_0x0749:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x075f
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0760
            L_0x075f:
            L_0x0760:
                java.lang.String r2 = r21.readString()
                r7.setGroupThreadConferenceUri(r1, r0, r2)
                r22.writeNoException()
                return r12
            L_0x076b:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.getGroupThreadOwner(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0781:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r7.setGroupThreadOwner(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0797:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.net.Uri r3 = r7.getGroupThreadIcon(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x07b2
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x07b5
            L_0x07b2:
                r10.writeInt(r1)
            L_0x07b5:
                return r12
            L_0x07b6:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x07cc
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x07cd
            L_0x07cc:
            L_0x07cd:
                java.lang.String r2 = r21.readString()
                r7.setGroupThreadIcon(r1, r0, r2)
                r22.writeNoException()
                return r12
            L_0x07d8:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r7.getGroupThreadName(r0, r1)
                r22.writeNoException()
                r10.writeString(r2)
                return r12
            L_0x07ee:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r7.setGroupThreadName(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0804:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.get1To1ThreadOtherParticipantId(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x081a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                long r2 = r7.get1To1ThreadFallbackThreadId(r0, r1)
                r22.writeNoException()
                r10.writeLong(r2)
                return r12
            L_0x0830:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                long r1 = r21.readLong()
                java.lang.String r3 = r21.readString()
                r7.set1To1ThreadFallbackThreadId(r0, r1, r3)
                r22.writeNoException()
                return r12
            L_0x0846:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsMessageSnippet r3 = r7.getMessageSnippet(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0861
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0864
            L_0x0861:
                r10.writeInt(r1)
            L_0x0864:
                return r12
            L_0x0865:
                r9.enforceInterface(r11)
                int r6 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0874
                r2 = r12
                goto L_0x0875
            L_0x0874:
                r2 = r1
            L_0x0875:
                int r13 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0881
                r4 = r12
                goto L_0x0882
            L_0x0881:
                r4 = r1
            L_0x0882:
                java.lang.String r14 = r21.readString()
                r0 = r19
                r1 = r6
                r3 = r13
                r5 = r14
                r0.deleteMessage(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r12
            L_0x0892:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x08a8
                android.os.Parcelable$Creator<android.telephony.ims.RcsOutgoingMessageCreationParams> r0 = android.telephony.ims.RcsOutgoingMessageCreationParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsOutgoingMessageCreationParams r0 = (android.telephony.ims.RcsOutgoingMessageCreationParams) r0
                goto L_0x08a9
            L_0x08a8:
            L_0x08a9:
                java.lang.String r2 = r21.readString()
                int r3 = r7.addOutgoingMessage(r1, r0, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x08b8:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x08ce
                android.os.Parcelable$Creator<android.telephony.ims.RcsIncomingMessageCreationParams> r0 = android.telephony.ims.RcsIncomingMessageCreationParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsIncomingMessageCreationParams r0 = (android.telephony.ims.RcsIncomingMessageCreationParams) r0
                goto L_0x08cf
            L_0x08ce:
            L_0x08cf:
                java.lang.String r2 = r21.readString()
                int r3 = r7.addIncomingMessage(r1, r0, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x08de:
                r9.enforceInterface(r11)
                int[] r1 = r21.createIntArray()
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x08f8
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x08f9
            L_0x08f8:
            L_0x08f9:
                java.lang.String r3 = r21.readString()
                int r4 = r7.createGroupThread(r1, r2, r0, r3)
                r22.writeNoException()
                r10.writeInt(r4)
                return r12
            L_0x0908:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                int r2 = r7.createRcs1To1Thread(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x091e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                boolean r3 = r7.deleteThread(r0, r1, r2)
                r22.writeNoException()
                r10.writeInt(r3)
                return r12
            L_0x0938:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x094a
                android.os.Parcelable$Creator<android.telephony.ims.RcsQueryContinuationToken> r0 = android.telephony.ims.RcsQueryContinuationToken.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsQueryContinuationToken r0 = (android.telephony.ims.RcsQueryContinuationToken) r0
                goto L_0x094b
            L_0x094a:
            L_0x094b:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsEventQueryResultDescriptor r3 = r7.getEventsWithToken(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x095f
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0962
            L_0x095f:
                r10.writeInt(r1)
            L_0x0962:
                return r12
            L_0x0963:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0975
                android.os.Parcelable$Creator<android.telephony.ims.RcsEventQueryParams> r0 = android.telephony.ims.RcsEventQueryParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsEventQueryParams r0 = (android.telephony.ims.RcsEventQueryParams) r0
                goto L_0x0976
            L_0x0975:
            L_0x0976:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsEventQueryResultDescriptor r3 = r7.getEvents(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x098a
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x098d
            L_0x098a:
                r10.writeInt(r1)
            L_0x098d:
                return r12
            L_0x098e:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x09a0
                android.os.Parcelable$Creator<android.telephony.ims.RcsQueryContinuationToken> r0 = android.telephony.ims.RcsQueryContinuationToken.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsQueryContinuationToken r0 = (android.telephony.ims.RcsQueryContinuationToken) r0
                goto L_0x09a1
            L_0x09a0:
            L_0x09a1:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsMessageQueryResultParcelable r3 = r7.getMessagesWithToken(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x09b5
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x09b8
            L_0x09b5:
                r10.writeInt(r1)
            L_0x09b8:
                return r12
            L_0x09b9:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x09cb
                android.os.Parcelable$Creator<android.telephony.ims.RcsMessageQueryParams> r0 = android.telephony.ims.RcsMessageQueryParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsMessageQueryParams r0 = (android.telephony.ims.RcsMessageQueryParams) r0
                goto L_0x09cc
            L_0x09cb:
            L_0x09cc:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsMessageQueryResultParcelable r3 = r7.getMessages(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x09e0
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x09e3
            L_0x09e0:
                r10.writeInt(r1)
            L_0x09e3:
                return r12
            L_0x09e4:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x09f6
                android.os.Parcelable$Creator<android.telephony.ims.RcsQueryContinuationToken> r0 = android.telephony.ims.RcsQueryContinuationToken.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsQueryContinuationToken r0 = (android.telephony.ims.RcsQueryContinuationToken) r0
                goto L_0x09f7
            L_0x09f6:
            L_0x09f7:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsParticipantQueryResultParcelable r3 = r7.getParticipantsWithToken(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0a0b
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0a0e
            L_0x0a0b:
                r10.writeInt(r1)
            L_0x0a0e:
                return r12
            L_0x0a0f:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0a21
                android.os.Parcelable$Creator<android.telephony.ims.RcsParticipantQueryParams> r0 = android.telephony.ims.RcsParticipantQueryParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsParticipantQueryParams r0 = (android.telephony.ims.RcsParticipantQueryParams) r0
                goto L_0x0a22
            L_0x0a21:
            L_0x0a22:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsParticipantQueryResultParcelable r3 = r7.getParticipants(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0a36
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0a39
            L_0x0a36:
                r10.writeInt(r1)
            L_0x0a39:
                return r12
            L_0x0a3a:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0a4c
                android.os.Parcelable$Creator<android.telephony.ims.RcsQueryContinuationToken> r0 = android.telephony.ims.RcsQueryContinuationToken.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsQueryContinuationToken r0 = (android.telephony.ims.RcsQueryContinuationToken) r0
                goto L_0x0a4d
            L_0x0a4c:
            L_0x0a4d:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsThreadQueryResultParcelable r3 = r7.getRcsThreadsWithToken(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0a61
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0a64
            L_0x0a61:
                r10.writeInt(r1)
            L_0x0a64:
                return r12
            L_0x0a65:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0a77
                android.os.Parcelable$Creator<android.telephony.ims.RcsThreadQueryParams> r0 = android.telephony.ims.RcsThreadQueryParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.telephony.ims.RcsThreadQueryParams r0 = (android.telephony.ims.RcsThreadQueryParams) r0
                goto L_0x0a78
            L_0x0a77:
            L_0x0a78:
                java.lang.String r2 = r21.readString()
                android.telephony.ims.RcsThreadQueryResultParcelable r3 = r7.getRcsThreads(r0, r2)
                r22.writeNoException()
                if (r3 == 0) goto L_0x0a8c
                r10.writeInt(r12)
                r3.writeToParcel(r10, r12)
                goto L_0x0a8f
            L_0x0a8c:
                r10.writeInt(r1)
            L_0x0a8f:
                return r12
            L_0x0a90:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.aidl.IRcs.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IRcs {
            public static IRcs sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public RcsThreadQueryResultParcelable getRcsThreads(RcsThreadQueryParams queryParams, String callingPackage) throws RemoteException {
                RcsThreadQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queryParams != null) {
                        _data.writeInt(1);
                        queryParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsThreads(queryParams, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsThreadQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsThreadQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsThreadQueryResultParcelable getRcsThreadsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
                RcsThreadQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (continuationToken != null) {
                        _data.writeInt(1);
                        continuationToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsThreadsWithToken(continuationToken, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsThreadQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsThreadQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsParticipantQueryResultParcelable getParticipants(RcsParticipantQueryParams queryParams, String callingPackage) throws RemoteException {
                RcsParticipantQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queryParams != null) {
                        _data.writeInt(1);
                        queryParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParticipants(queryParams, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsParticipantQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsParticipantQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsParticipantQueryResultParcelable getParticipantsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
                RcsParticipantQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (continuationToken != null) {
                        _data.writeInt(1);
                        continuationToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParticipantsWithToken(continuationToken, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsParticipantQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsParticipantQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsMessageQueryResultParcelable getMessages(RcsMessageQueryParams queryParams, String callingPackage) throws RemoteException {
                RcsMessageQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queryParams != null) {
                        _data.writeInt(1);
                        queryParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessages(queryParams, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsMessageQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsMessageQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsMessageQueryResultParcelable getMessagesWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
                RcsMessageQueryResultParcelable _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (continuationToken != null) {
                        _data.writeInt(1);
                        continuationToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessagesWithToken(continuationToken, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsMessageQueryResultParcelable.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsMessageQueryResultParcelable _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsEventQueryResultDescriptor getEvents(RcsEventQueryParams queryParams, String callingPackage) throws RemoteException {
                RcsEventQueryResultDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (queryParams != null) {
                        _data.writeInt(1);
                        queryParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEvents(queryParams, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsEventQueryResultDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsEventQueryResultDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsEventQueryResultDescriptor getEventsWithToken(RcsQueryContinuationToken continuationToken, String callingPackage) throws RemoteException {
                RcsEventQueryResultDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (continuationToken != null) {
                        _data.writeInt(1);
                        continuationToken.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEventsWithToken(continuationToken, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsEventQueryResultDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsEventQueryResultDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean deleteThread(int threadId, int threadType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(threadId);
                    _data.writeInt(threadType);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteThread(threadId, threadType, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createRcs1To1Thread(int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createRcs1To1Thread(participantId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createGroupThread(int[] participantIds, String groupName, Uri groupIcon, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(participantIds);
                    _data.writeString(groupName);
                    if (groupIcon != null) {
                        _data.writeInt(1);
                        groupIcon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createGroupThread(participantIds, groupName, groupIcon, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int addIncomingMessage(int rcsThreadId, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    if (rcsIncomingMessageCreationParams != null) {
                        _data.writeInt(1);
                        rcsIncomingMessageCreationParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addIncomingMessage(rcsThreadId, rcsIncomingMessageCreationParams, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int addOutgoingMessage(int rcsThreadId, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    if (rcsOutgoingMessageCreationParams != null) {
                        _data.writeInt(1);
                        rcsOutgoingMessageCreationParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addOutgoingMessage(rcsThreadId, rcsOutgoingMessageCreationParams, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteMessage(int rcsMessageId, boolean isIncoming, int rcsThreadId, boolean isGroup, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsMessageId);
                    _data.writeInt(isIncoming);
                    _data.writeInt(rcsThreadId);
                    _data.writeInt(isGroup);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteMessage(rcsMessageId, isIncoming, rcsThreadId, isGroup, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RcsMessageSnippet getMessageSnippet(int rcsThreadId, String callingPackage) throws RemoteException {
                RcsMessageSnippet _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageSnippet(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RcsMessageSnippet.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RcsMessageSnippet _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void set1To1ThreadFallbackThreadId(int rcsThreadId, long fallbackId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeLong(fallbackId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().set1To1ThreadFallbackThreadId(rcsThreadId, fallbackId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long get1To1ThreadFallbackThreadId(int rcsThreadId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().get1To1ThreadFallbackThreadId(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int get1To1ThreadOtherParticipantId(int rcsThreadId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().get1To1ThreadOtherParticipantId(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGroupThreadName(int rcsThreadId, String groupName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(groupName);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGroupThreadName(rcsThreadId, groupName, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getGroupThreadName(int rcsThreadId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGroupThreadName(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGroupThreadIcon(int rcsThreadId, Uri groupIcon, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    if (groupIcon != null) {
                        _data.writeInt(1);
                        groupIcon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGroupThreadIcon(rcsThreadId, groupIcon, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri getGroupThreadIcon(int rcsThreadId, String callingPackage) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGroupThreadIcon(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGroupThreadOwner(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGroupThreadOwner(rcsThreadId, participantId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getGroupThreadOwner(int rcsThreadId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGroupThreadOwner(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGroupThreadConferenceUri(int rcsThreadId, Uri conferenceUri, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    if (conferenceUri != null) {
                        _data.writeInt(1);
                        conferenceUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGroupThreadConferenceUri(rcsThreadId, conferenceUri, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri getGroupThreadConferenceUri(int rcsThreadId, String callingPackage) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGroupThreadConferenceUri(rcsThreadId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addParticipantToGroupThread(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addParticipantToGroupThread(rcsThreadId, participantId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeParticipantFromGroupThread(int rcsThreadId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rcsThreadId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeParticipantFromGroupThread(rcsThreadId, participantId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createRcsParticipant(String canonicalAddress, String alias, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(canonicalAddress);
                    _data.writeString(alias);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createRcsParticipant(canonicalAddress, alias, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getRcsParticipantCanonicalAddress(int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsParticipantCanonicalAddress(participantId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getRcsParticipantAlias(int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsParticipantAlias(participantId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRcsParticipantAlias(int id, String alias, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeString(alias);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRcsParticipantAlias(id, alias, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getRcsParticipantContactId(int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsParticipantContactId(participantId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRcsParticipantContactId(int participantId, String contactId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(participantId);
                    _data.writeString(contactId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRcsParticipantContactId(participantId, contactId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMessageSubId(int messageId, boolean isIncoming, int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMessageSubId(messageId, isIncoming, subId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMessageSubId(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageSubId(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMessageStatus(int messageId, boolean isIncoming, int status, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeInt(status);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMessageStatus(messageId, isIncoming, status, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMessageStatus(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageStatus(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMessageOriginationTimestamp(int messageId, boolean isIncoming, long originationTimestamp, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeLong(originationTimestamp);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMessageOriginationTimestamp(messageId, isIncoming, originationTimestamp, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getMessageOriginationTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageOriginationTimestamp(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGlobalMessageIdForMessage(int messageId, boolean isIncoming, String globalId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(globalId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGlobalMessageIdForMessage(messageId, isIncoming, globalId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getGlobalMessageIdForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGlobalMessageIdForMessage(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMessageArrivalTimestamp(int messageId, boolean isIncoming, long arrivalTimestamp, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeLong(arrivalTimestamp);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMessageArrivalTimestamp(messageId, isIncoming, arrivalTimestamp, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getMessageArrivalTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageArrivalTimestamp(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMessageSeenTimestamp(int messageId, boolean isIncoming, long seenTimestamp, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeLong(seenTimestamp);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMessageSeenTimestamp(messageId, isIncoming, seenTimestamp, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getMessageSeenTimestamp(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageSeenTimestamp(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTextForMessage(int messageId, boolean isIncoming, String text, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(text);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTextForMessage(messageId, isIncoming, text, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getTextForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTextForMessage(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLatitudeForMessage(int messageId, boolean isIncoming, double latitude, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeDouble(latitude);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLatitudeForMessage(messageId, isIncoming, latitude, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public double getLatitudeForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLatitudeForMessage(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    double _result = _reply.readDouble();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setLongitudeForMessage(int messageId, boolean isIncoming, double longitude, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeDouble(longitude);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setLongitudeForMessage(messageId, isIncoming, longitude, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public double getLongitudeForMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLongitudeForMessage(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    double _result = _reply.readDouble();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getFileTransfersAttachedToMessage(int messageId, boolean isIncoming, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransfersAttachedToMessage(messageId, isIncoming, callingPackage);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSenderParticipant(int messageId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSenderParticipant(messageId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getMessageRecipients(int messageId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMessageRecipients(messageId, callingPackage);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getOutgoingDeliveryDeliveredTimestamp(int messageId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOutgoingDeliveryDeliveredTimestamp(messageId, participantId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOutgoingDeliveryDeliveredTimestamp(int messageId, int participantId, long deliveredTimestamp, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeLong(deliveredTimestamp);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOutgoingDeliveryDeliveredTimestamp(messageId, participantId, deliveredTimestamp, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getOutgoingDeliverySeenTimestamp(int messageId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOutgoingDeliverySeenTimestamp(messageId, participantId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOutgoingDeliverySeenTimestamp(int messageId, int participantId, long seenTimestamp, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeLong(seenTimestamp);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOutgoingDeliverySeenTimestamp(messageId, participantId, seenTimestamp, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getOutgoingDeliveryStatus(int messageId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(60, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOutgoingDeliveryStatus(messageId, participantId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOutgoingDeliveryStatus(int messageId, int participantId, int status, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(participantId);
                    _data.writeInt(status);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setOutgoingDeliveryStatus(messageId, participantId, status, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int storeFileTransfer(int messageId, boolean isIncoming, RcsFileTransferCreationParams fileTransferCreationParams, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageId);
                    _data.writeInt(isIncoming);
                    if (fileTransferCreationParams != null) {
                        _data.writeInt(1);
                        fileTransferCreationParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().storeFileTransfer(messageId, isIncoming, fileTransferCreationParams, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteFileTransfer(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteFileTransfer(partId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferSessionId(int partId, String sessionId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(sessionId);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferSessionId(partId, sessionId, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getFileTransferSessionId(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferSessionId(partId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferContentUri(int partId, Uri contentUri, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    if (contentUri != null) {
                        _data.writeInt(1);
                        contentUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferContentUri(partId, contentUri, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri getFileTransferContentUri(int partId, String callingPackage) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferContentUri(partId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferContentType(int partId, String contentType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(contentType);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferContentType(partId, contentType, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getFileTransferContentType(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferContentType(partId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferFileSize(int partId, long fileSize, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeLong(fileSize);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferFileSize(partId, fileSize, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getFileTransferFileSize(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(71, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferFileSize(partId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferTransferOffset(int partId, long transferOffset, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeLong(transferOffset);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferTransferOffset(partId, transferOffset, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getFileTransferTransferOffset(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferTransferOffset(partId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferStatus(int partId, int transferStatus, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeInt(transferStatus);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(74, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferStatus(partId, transferStatus, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getFileTransferStatus(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferStatus(partId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferWidth(int partId, int width, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeInt(width);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(76, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferWidth(partId, width, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getFileTransferWidth(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferWidth(partId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferHeight(int partId, int height, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeInt(height);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferHeight(partId, height, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getFileTransferHeight(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(79, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferHeight(partId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferLength(int partId, long length, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeLong(length);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferLength(partId, length, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getFileTransferLength(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferLength(partId, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferPreviewUri(int partId, Uri uri, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(82, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferPreviewUri(partId, uri, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri getFileTransferPreviewUri(int partId, String callingPackage) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferPreviewUri(partId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setFileTransferPreviewType(int partId, String type, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(type);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(84, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setFileTransferPreviewType(partId, type, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getFileTransferPreviewType(int partId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(partId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFileTransferPreviewType(partId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createGroupThreadNameChangedEvent(long timestamp, int threadId, int originationParticipantId, String newName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(timestamp);
                        try {
                            _data.writeInt(threadId);
                        } catch (Throwable th) {
                            th = th;
                            int i = originationParticipantId;
                            String str = newName;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(originationParticipantId);
                            try {
                                _data.writeString(newName);
                            } catch (Throwable th2) {
                                th = th2;
                                String str22 = callingPackage;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int createGroupThreadNameChangedEvent = Stub.getDefaultImpl().createGroupThreadNameChangedEvent(timestamp, threadId, originationParticipantId, newName, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return createGroupThreadNameChangedEvent;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            String str3 = newName;
                            String str222 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i2 = threadId;
                        int i3 = originationParticipantId;
                        String str32 = newName;
                        String str2222 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    long j = timestamp;
                    int i22 = threadId;
                    int i32 = originationParticipantId;
                    String str322 = newName;
                    String str22222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int createGroupThreadIconChangedEvent(long timestamp, int threadId, int originationParticipantId, Uri newIcon, String callingPackage) throws RemoteException {
                Uri uri = newIcon;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(timestamp);
                        try {
                            _data.writeInt(threadId);
                        } catch (Throwable th) {
                            th = th;
                            int i = originationParticipantId;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(originationParticipantId);
                            if (uri != null) {
                                _data.writeInt(1);
                                uri.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(87, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int createGroupThreadIconChangedEvent = Stub.getDefaultImpl().createGroupThreadIconChangedEvent(timestamp, threadId, originationParticipantId, newIcon, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return createGroupThreadIconChangedEvent;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i2 = threadId;
                        int i3 = originationParticipantId;
                        String str22 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    long j = timestamp;
                    int i22 = threadId;
                    int i32 = originationParticipantId;
                    String str222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int createGroupThreadParticipantJoinedEvent(long timestamp, int threadId, int originationParticipantId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(timestamp);
                        try {
                            _data.writeInt(threadId);
                        } catch (Throwable th) {
                            th = th;
                            int i = originationParticipantId;
                            int i2 = participantId;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(originationParticipantId);
                            try {
                                _data.writeInt(participantId);
                            } catch (Throwable th2) {
                                th = th2;
                                String str2 = callingPackage;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int createGroupThreadParticipantJoinedEvent = Stub.getDefaultImpl().createGroupThreadParticipantJoinedEvent(timestamp, threadId, originationParticipantId, participantId, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return createGroupThreadParticipantJoinedEvent;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = participantId;
                            String str22 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = threadId;
                        int i4 = originationParticipantId;
                        int i222 = participantId;
                        String str222 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    long j = timestamp;
                    int i32 = threadId;
                    int i42 = originationParticipantId;
                    int i2222 = participantId;
                    String str2222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int createGroupThreadParticipantLeftEvent(long timestamp, int threadId, int originationParticipantId, int participantId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(timestamp);
                        try {
                            _data.writeInt(threadId);
                        } catch (Throwable th) {
                            th = th;
                            int i = originationParticipantId;
                            int i2 = participantId;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(originationParticipantId);
                            try {
                                _data.writeInt(participantId);
                            } catch (Throwable th2) {
                                th = th2;
                                String str2 = callingPackage;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    int _result = _reply.readInt();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                int createGroupThreadParticipantLeftEvent = Stub.getDefaultImpl().createGroupThreadParticipantLeftEvent(timestamp, threadId, originationParticipantId, participantId, callingPackage);
                                _reply.recycle();
                                _data.recycle();
                                return createGroupThreadParticipantLeftEvent;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = participantId;
                            String str22 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i3 = threadId;
                        int i4 = originationParticipantId;
                        int i222 = participantId;
                        String str222 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    long j = timestamp;
                    int i32 = threadId;
                    int i42 = originationParticipantId;
                    int i2222 = participantId;
                    String str2222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int createParticipantAliasChangedEvent(long timestamp, int participantId, String newAlias, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timestamp);
                    _data.writeInt(participantId);
                    _data.writeString(newAlias);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createParticipantAliasChangedEvent(timestamp, participantId, newAlias, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRcs impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IRcs getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
