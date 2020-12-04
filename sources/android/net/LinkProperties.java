package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public final class LinkProperties implements Parcelable {
    public static final Parcelable.Creator<LinkProperties> CREATOR = new Parcelable.Creator<LinkProperties>() {
        public LinkProperties createFromParcel(Parcel in) {
            LinkProperties netProp = new LinkProperties();
            String iface = in.readString();
            if (iface != null) {
                netProp.setInterfaceName(iface);
            }
            int addressCount = in.readInt();
            for (int i = 0; i < addressCount; i++) {
                netProp.addLinkAddress((LinkAddress) in.readParcelable((ClassLoader) null));
            }
            int addressCount2 = in.readInt();
            for (int i2 = 0; i2 < addressCount2; i2++) {
                try {
                    netProp.addDnsServer(InetAddress.getByAddress(in.createByteArray()));
                } catch (UnknownHostException e) {
                }
            }
            int addressCount3 = in.readInt();
            for (int i3 = 0; i3 < addressCount3; i3++) {
                try {
                    netProp.addValidatedPrivateDnsServer(InetAddress.getByAddress(in.createByteArray()));
                } catch (UnknownHostException e2) {
                }
            }
            netProp.setUsePrivateDns(in.readBoolean());
            netProp.setPrivateDnsServerName(in.readString());
            int addressCount4 = in.readInt();
            for (int i4 = 0; i4 < addressCount4; i4++) {
                try {
                    netProp.addPcscfServer(InetAddress.getByAddress(in.createByteArray()));
                } catch (UnknownHostException e3) {
                }
            }
            netProp.setDomains(in.readString());
            netProp.setMtu(in.readInt());
            netProp.setTcpBufferSizes(in.readString());
            int addressCount5 = in.readInt();
            for (int i5 = 0; i5 < addressCount5; i5++) {
                netProp.addRoute((RouteInfo) in.readParcelable((ClassLoader) null));
            }
            if (in.readByte() == 1) {
                netProp.setHttpProxy((ProxyInfo) in.readParcelable((ClassLoader) null));
            }
            netProp.setNat64Prefix((IpPrefix) in.readParcelable((ClassLoader) null));
            ArrayList<LinkProperties> stackedLinks = new ArrayList<>();
            in.readList(stackedLinks, LinkProperties.class.getClassLoader());
            Iterator<LinkProperties> it = stackedLinks.iterator();
            while (it.hasNext()) {
                netProp.addStackedLink(it.next());
            }
            return netProp;
        }

        public LinkProperties[] newArray(int size) {
            return new LinkProperties[size];
        }
    };
    private static final int MAX_MTU = 10000;
    private static final int MIN_MTU = 68;
    private static final int MIN_MTU_V6 = 1280;
    private final ArrayList<InetAddress> mDnses = new ArrayList<>();
    private String mDomains;
    private ProxyInfo mHttpProxy;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private String mIfaceName;
    private final ArrayList<LinkAddress> mLinkAddresses = new ArrayList<>();
    private int mMtu;
    private IpPrefix mNat64Prefix;
    private final ArrayList<InetAddress> mPcscfs = new ArrayList<>();
    private String mPrivateDnsServerName;
    private ArrayList<RouteInfo> mRoutes = new ArrayList<>();
    private Hashtable<String, LinkProperties> mStackedLinks = new Hashtable<>();
    private String mTcpBufferSizes;
    private boolean mUsePrivateDns;
    private final ArrayList<InetAddress> mValidatedPrivateDnses = new ArrayList<>();

    public enum ProvisioningChange {
        STILL_NOT_PROVISIONED,
        LOST_PROVISIONING,
        GAINED_PROVISIONING,
        STILL_PROVISIONED
    }

    public static class CompareResult<T> {
        public final List<T> added = new ArrayList();
        public final List<T> removed = new ArrayList();

        public CompareResult() {
        }

        public CompareResult(Collection<T> oldItems, Collection<T> newItems) {
            if (oldItems != null) {
                this.removed.addAll(oldItems);
            }
            if (newItems != null) {
                for (T newItem : newItems) {
                    if (!this.removed.remove(newItem)) {
                        this.added.add(newItem);
                    }
                }
            }
        }

        public String toString() {
            return "removed=[" + TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.removed) + "] added=[" + TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.added) + "]";
        }
    }

    @UnsupportedAppUsage
    public static ProvisioningChange compareProvisioning(LinkProperties before, LinkProperties after) {
        if (!before.isProvisioned() || !after.isProvisioned()) {
            if (before.isProvisioned() && !after.isProvisioned()) {
                return ProvisioningChange.LOST_PROVISIONING;
            }
            if (before.isProvisioned() || !after.isProvisioned()) {
                return ProvisioningChange.STILL_NOT_PROVISIONED;
            }
            return ProvisioningChange.GAINED_PROVISIONING;
        } else if ((!before.isIpv4Provisioned() || after.isIpv4Provisioned()) && (!before.isIpv6Provisioned() || after.isIpv6Provisioned())) {
            return ProvisioningChange.STILL_PROVISIONED;
        } else {
            return ProvisioningChange.LOST_PROVISIONING;
        }
    }

    public LinkProperties() {
    }

    @SystemApi
    public LinkProperties(LinkProperties source) {
        if (source != null) {
            this.mIfaceName = source.mIfaceName;
            this.mLinkAddresses.addAll(source.mLinkAddresses);
            this.mDnses.addAll(source.mDnses);
            this.mValidatedPrivateDnses.addAll(source.mValidatedPrivateDnses);
            this.mUsePrivateDns = source.mUsePrivateDns;
            this.mPrivateDnsServerName = source.mPrivateDnsServerName;
            this.mPcscfs.addAll(source.mPcscfs);
            this.mDomains = source.mDomains;
            this.mRoutes.addAll(source.mRoutes);
            this.mHttpProxy = source.mHttpProxy == null ? null : new ProxyInfo(source.mHttpProxy);
            for (LinkProperties l : source.mStackedLinks.values()) {
                addStackedLink(l);
            }
            setMtu(source.mMtu);
            this.mTcpBufferSizes = source.mTcpBufferSizes;
            this.mNat64Prefix = source.mNat64Prefix;
        }
    }

    public void setInterfaceName(String iface) {
        this.mIfaceName = iface;
        ArrayList<RouteInfo> newRoutes = new ArrayList<>(this.mRoutes.size());
        Iterator<RouteInfo> it = this.mRoutes.iterator();
        while (it.hasNext()) {
            newRoutes.add(routeWithInterface(it.next()));
        }
        this.mRoutes = newRoutes;
    }

    public String getInterfaceName() {
        return this.mIfaceName;
    }

    @UnsupportedAppUsage
    public List<String> getAllInterfaceNames() {
        List<String> interfaceNames = new ArrayList<>(this.mStackedLinks.size() + 1);
        if (this.mIfaceName != null) {
            interfaceNames.add(this.mIfaceName);
        }
        for (LinkProperties stacked : this.mStackedLinks.values()) {
            interfaceNames.addAll(stacked.getAllInterfaceNames());
        }
        return interfaceNames;
    }

    @UnsupportedAppUsage
    public List<InetAddress> getAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            addresses.add(it.next().getAddress());
        }
        return Collections.unmodifiableList(addresses);
    }

    @UnsupportedAppUsage
    public List<InetAddress> getAllAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            addresses.add(it.next().getAddress());
        }
        for (LinkProperties stacked : this.mStackedLinks.values()) {
            addresses.addAll(stacked.getAllAddresses());
        }
        return addresses;
    }

    private int findLinkAddressIndex(LinkAddress address) {
        for (int i = 0; i < this.mLinkAddresses.size(); i++) {
            if (this.mLinkAddresses.get(i).isSameAddressAs(address)) {
                return i;
            }
        }
        return -1;
    }

    @SystemApi
    public boolean addLinkAddress(LinkAddress address) {
        if (address == null) {
            return false;
        }
        int i = findLinkAddressIndex(address);
        if (i < 0) {
            this.mLinkAddresses.add(address);
            return true;
        } else if (this.mLinkAddresses.get(i).equals(address)) {
            return false;
        } else {
            this.mLinkAddresses.set(i, address);
            return true;
        }
    }

    @SystemApi
    public boolean removeLinkAddress(LinkAddress toRemove) {
        int i = findLinkAddressIndex(toRemove);
        if (i < 0) {
            return false;
        }
        this.mLinkAddresses.remove(i);
        return true;
    }

    public List<LinkAddress> getLinkAddresses() {
        return Collections.unmodifiableList(this.mLinkAddresses);
    }

    @UnsupportedAppUsage
    public List<LinkAddress> getAllLinkAddresses() {
        List<LinkAddress> addresses = new ArrayList<>(this.mLinkAddresses);
        for (LinkProperties stacked : this.mStackedLinks.values()) {
            addresses.addAll(stacked.getAllLinkAddresses());
        }
        return addresses;
    }

    public void setLinkAddresses(Collection<LinkAddress> addresses) {
        this.mLinkAddresses.clear();
        for (LinkAddress address : addresses) {
            addLinkAddress(address);
        }
    }

    @SystemApi
    public boolean addDnsServer(InetAddress dnsServer) {
        if (dnsServer == null || this.mDnses.contains(dnsServer)) {
            return false;
        }
        this.mDnses.add(dnsServer);
        return true;
    }

    @SystemApi
    public boolean removeDnsServer(InetAddress dnsServer) {
        return this.mDnses.remove(dnsServer);
    }

    public void setDnsServers(Collection<InetAddress> dnsServers) {
        this.mDnses.clear();
        for (InetAddress dnsServer : dnsServers) {
            addDnsServer(dnsServer);
        }
    }

    public List<InetAddress> getDnsServers() {
        return Collections.unmodifiableList(this.mDnses);
    }

    @SystemApi
    public void setUsePrivateDns(boolean usePrivateDns) {
        this.mUsePrivateDns = usePrivateDns;
    }

    public boolean isPrivateDnsActive() {
        return this.mUsePrivateDns;
    }

    @SystemApi
    public void setPrivateDnsServerName(String privateDnsServerName) {
        this.mPrivateDnsServerName = privateDnsServerName;
    }

    public String getPrivateDnsServerName() {
        return this.mPrivateDnsServerName;
    }

    public boolean addValidatedPrivateDnsServer(InetAddress dnsServer) {
        if (dnsServer == null || this.mValidatedPrivateDnses.contains(dnsServer)) {
            return false;
        }
        this.mValidatedPrivateDnses.add(dnsServer);
        return true;
    }

    public boolean removeValidatedPrivateDnsServer(InetAddress dnsServer) {
        return this.mValidatedPrivateDnses.remove(dnsServer);
    }

    @SystemApi
    public void setValidatedPrivateDnsServers(Collection<InetAddress> dnsServers) {
        this.mValidatedPrivateDnses.clear();
        for (InetAddress dnsServer : dnsServers) {
            addValidatedPrivateDnsServer(dnsServer);
        }
    }

    @SystemApi
    public List<InetAddress> getValidatedPrivateDnsServers() {
        return Collections.unmodifiableList(this.mValidatedPrivateDnses);
    }

    public boolean addPcscfServer(InetAddress pcscfServer) {
        if (pcscfServer == null || this.mPcscfs.contains(pcscfServer)) {
            return false;
        }
        this.mPcscfs.add(pcscfServer);
        return true;
    }

    public boolean removePcscfServer(InetAddress pcscfServer) {
        return this.mPcscfs.remove(pcscfServer);
    }

    @SystemApi
    public void setPcscfServers(Collection<InetAddress> pcscfServers) {
        this.mPcscfs.clear();
        for (InetAddress pcscfServer : pcscfServers) {
            addPcscfServer(pcscfServer);
        }
    }

    @SystemApi
    public List<InetAddress> getPcscfServers() {
        return Collections.unmodifiableList(this.mPcscfs);
    }

    public void setDomains(String domains) {
        this.mDomains = domains;
    }

    public String getDomains() {
        return this.mDomains;
    }

    public void setMtu(int mtu) {
        this.mMtu = mtu;
    }

    public int getMtu() {
        return this.mMtu;
    }

    @SystemApi
    public void setTcpBufferSizes(String tcpBufferSizes) {
        this.mTcpBufferSizes = tcpBufferSizes;
    }

    @SystemApi
    public String getTcpBufferSizes() {
        return this.mTcpBufferSizes;
    }

    private RouteInfo routeWithInterface(RouteInfo route) {
        return new RouteInfo(route.getDestination(), route.getGateway(), this.mIfaceName, route.getType());
    }

    public boolean addRoute(RouteInfo route) {
        String routeIface = route.getInterface();
        if (routeIface == null || routeIface.equals(this.mIfaceName)) {
            RouteInfo route2 = routeWithInterface(route);
            if (this.mRoutes.contains(route2)) {
                return false;
            }
            this.mRoutes.add(route2);
            return true;
        }
        throw new IllegalArgumentException("Route added with non-matching interface: " + routeIface + " vs. " + this.mIfaceName);
    }

    @SystemApi
    public boolean removeRoute(RouteInfo route) {
        return Objects.equals(this.mIfaceName, route.getInterface()) && this.mRoutes.remove(route);
    }

    public List<RouteInfo> getRoutes() {
        return Collections.unmodifiableList(this.mRoutes);
    }

    public void ensureDirectlyConnectedRoutes() {
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            addRoute(new RouteInfo(it.next(), (InetAddress) null, this.mIfaceName));
        }
    }

    @UnsupportedAppUsage
    public List<RouteInfo> getAllRoutes() {
        List<RouteInfo> routes = new ArrayList<>(this.mRoutes);
        for (LinkProperties stacked : this.mStackedLinks.values()) {
            routes.addAll(stacked.getAllRoutes());
        }
        return routes;
    }

    public void setHttpProxy(ProxyInfo proxy) {
        this.mHttpProxy = proxy;
    }

    public ProxyInfo getHttpProxy() {
        return this.mHttpProxy;
    }

    @SystemApi
    public IpPrefix getNat64Prefix() {
        return this.mNat64Prefix;
    }

    @SystemApi
    public void setNat64Prefix(IpPrefix prefix) {
        if (prefix == null || prefix.getPrefixLength() == 96) {
            this.mNat64Prefix = prefix;
            return;
        }
        throw new IllegalArgumentException("Only 96-bit prefixes are supported: " + prefix);
    }

    @UnsupportedAppUsage
    public boolean addStackedLink(LinkProperties link) {
        if (link.getInterfaceName() == null) {
            return false;
        }
        this.mStackedLinks.put(link.getInterfaceName(), link);
        return true;
    }

    public boolean removeStackedLink(String iface) {
        return this.mStackedLinks.remove(iface) != null;
    }

    @UnsupportedAppUsage
    public List<LinkProperties> getStackedLinks() {
        if (this.mStackedLinks.isEmpty()) {
            return Collections.emptyList();
        }
        List<LinkProperties> stacked = new ArrayList<>();
        for (LinkProperties link : this.mStackedLinks.values()) {
            stacked.add(new LinkProperties(link));
        }
        return Collections.unmodifiableList(stacked);
    }

    public void clear() {
        this.mIfaceName = null;
        this.mLinkAddresses.clear();
        this.mDnses.clear();
        this.mUsePrivateDns = false;
        this.mPrivateDnsServerName = null;
        this.mPcscfs.clear();
        this.mDomains = null;
        this.mRoutes.clear();
        this.mHttpProxy = null;
        this.mStackedLinks.clear();
        this.mMtu = 0;
        this.mTcpBufferSizes = null;
        this.mNat64Prefix = null;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringJoiner resultJoiner = new StringJoiner(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER, "{", "}");
        if (this.mIfaceName != null) {
            resultJoiner.add("InterfaceName:");
            resultJoiner.add(this.mIfaceName);
        }
        resultJoiner.add("LinkAddresses: [");
        if (!this.mLinkAddresses.isEmpty()) {
            resultJoiner.add(TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.mLinkAddresses));
        }
        resultJoiner.add("]");
        resultJoiner.add("DnsAddresses: [");
        if (!this.mDnses.isEmpty()) {
            resultJoiner.add(TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.mDnses));
        }
        resultJoiner.add("]");
        if (this.mUsePrivateDns) {
            resultJoiner.add("UsePrivateDns: true");
        }
        if (this.mPrivateDnsServerName != null) {
            resultJoiner.add("PrivateDnsServerName:");
            resultJoiner.add(this.mPrivateDnsServerName);
        }
        if (!this.mPcscfs.isEmpty()) {
            resultJoiner.add("PcscfAddresses: [");
            resultJoiner.add(TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.mPcscfs));
            resultJoiner.add("]");
        }
        if (!this.mValidatedPrivateDnses.isEmpty()) {
            StringJoiner validatedPrivateDnsesJoiner = new StringJoiner(SmsManager.REGEX_PREFIX_DELIMITER, "ValidatedPrivateDnsAddresses: [", "]");
            Iterator<InetAddress> it = this.mValidatedPrivateDnses.iterator();
            while (it.hasNext()) {
                validatedPrivateDnsesJoiner.add(it.next().getHostAddress());
            }
            resultJoiner.add(validatedPrivateDnsesJoiner.toString());
        }
        resultJoiner.add("Domains:");
        resultJoiner.add(this.mDomains);
        resultJoiner.add("MTU:");
        resultJoiner.add(Integer.toString(this.mMtu));
        if (this.mTcpBufferSizes != null) {
            resultJoiner.add("TcpBufferSizes:");
            resultJoiner.add(this.mTcpBufferSizes);
        }
        resultJoiner.add("Routes: [");
        if (!this.mRoutes.isEmpty()) {
            resultJoiner.add(TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, (Iterable) this.mRoutes));
        }
        resultJoiner.add("]");
        if (this.mHttpProxy != null) {
            resultJoiner.add("HttpProxy:");
            resultJoiner.add(this.mHttpProxy.toString());
        }
        if (this.mNat64Prefix != null) {
            resultJoiner.add("Nat64Prefix:");
            resultJoiner.add(this.mNat64Prefix.toString());
        }
        Collection<LinkProperties> stackedLinksValues = this.mStackedLinks.values();
        if (!stackedLinksValues.isEmpty()) {
            StringJoiner stackedLinksJoiner = new StringJoiner(SmsManager.REGEX_PREFIX_DELIMITER, "Stacked: [", "]");
            for (LinkProperties lp : stackedLinksValues) {
                stackedLinksJoiner.add("[ " + lp + " ]");
            }
            resultJoiner.add(stackedLinksJoiner.toString());
        }
        return resultJoiner.toString();
    }

    @SystemApi
    public boolean hasIpv4Address() {
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            if (it.next().getAddress() instanceof Inet4Address) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasIPv4Address() {
        return hasIpv4Address();
    }

    private boolean hasIpv4AddressOnInterface(String iface) {
        return (Objects.equals(iface, this.mIfaceName) && hasIpv4Address()) || (iface != null && this.mStackedLinks.containsKey(iface) && this.mStackedLinks.get(iface).hasIpv4Address());
    }

    @SystemApi
    public boolean hasGlobalIpv6Address() {
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            LinkAddress address = it.next();
            if ((address.getAddress() instanceof Inet6Address) && address.isGlobalPreferred()) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasGlobalIPv6Address() {
        return hasGlobalIpv6Address();
    }

    @UnsupportedAppUsage
    public boolean hasIpv4DefaultRoute() {
        Iterator<RouteInfo> it = this.mRoutes.iterator();
        while (it.hasNext()) {
            if (it.next().isIPv4Default()) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasIPv4DefaultRoute() {
        return hasIpv4DefaultRoute();
    }

    @SystemApi
    public boolean hasIpv6DefaultRoute() {
        Iterator<RouteInfo> it = this.mRoutes.iterator();
        while (it.hasNext()) {
            if (it.next().isIPv6Default()) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasIPv6DefaultRoute() {
        return hasIpv6DefaultRoute();
    }

    @UnsupportedAppUsage
    public boolean hasIpv4DnsServer() {
        Iterator<InetAddress> it = this.mDnses.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof Inet4Address) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasIPv4DnsServer() {
        return hasIpv4DnsServer();
    }

    @UnsupportedAppUsage
    public boolean hasIpv6DnsServer() {
        Iterator<InetAddress> it = this.mDnses.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof Inet6Address) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean hasIPv6DnsServer() {
        return hasIpv6DnsServer();
    }

    public boolean hasIpv4PcscfServer() {
        Iterator<InetAddress> it = this.mPcscfs.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof Inet4Address) {
                return true;
            }
        }
        return false;
    }

    public boolean hasIpv6PcscfServer() {
        Iterator<InetAddress> it = this.mPcscfs.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof Inet6Address) {
                return true;
            }
        }
        return false;
    }

    @SystemApi
    public boolean isIpv4Provisioned() {
        return hasIpv4Address() && hasIpv4DefaultRoute() && hasIpv4DnsServer();
    }

    @SystemApi
    public boolean isIpv6Provisioned() {
        return hasGlobalIpv6Address() && hasIpv6DefaultRoute() && hasIpv6DnsServer();
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean isIPv6Provisioned() {
        return isIpv6Provisioned();
    }

    @SystemApi
    public boolean isProvisioned() {
        return isIpv4Provisioned() || isIpv6Provisioned();
    }

    @SystemApi
    public boolean isReachable(InetAddress ip) {
        RouteInfo bestRoute = RouteInfo.selectBestRoute(getAllRoutes(), ip);
        if (bestRoute == null) {
            return false;
        }
        if (ip instanceof Inet4Address) {
            return hasIpv4AddressOnInterface(bestRoute.getInterface());
        }
        if (!(ip instanceof Inet6Address)) {
            return false;
        }
        if (ip.isLinkLocalAddress()) {
            if (((Inet6Address) ip).getScopeId() != 0) {
                return true;
            }
            return false;
        } else if (!bestRoute.hasGateway() || hasGlobalIpv6Address()) {
            return true;
        } else {
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean isIdenticalInterfaceName(LinkProperties target) {
        return TextUtils.equals(getInterfaceName(), target.getInterfaceName());
    }

    @UnsupportedAppUsage
    public boolean isIdenticalAddresses(LinkProperties target) {
        Collection<InetAddress> targetAddresses = target.getAddresses();
        Collection<InetAddress> sourceAddresses = getAddresses();
        if (sourceAddresses.size() == targetAddresses.size()) {
            return sourceAddresses.containsAll(targetAddresses);
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalDnses(LinkProperties target) {
        Collection<InetAddress> targetDnses = target.getDnsServers();
        String targetDomains = target.getDomains();
        if (this.mDomains == null) {
            if (targetDomains != null) {
                return false;
            }
        } else if (!this.mDomains.equals(targetDomains)) {
            return false;
        }
        if (this.mDnses.size() == targetDnses.size()) {
            return this.mDnses.containsAll(targetDnses);
        }
        return false;
    }

    public boolean isIdenticalPrivateDns(LinkProperties target) {
        return isPrivateDnsActive() == target.isPrivateDnsActive() && TextUtils.equals(getPrivateDnsServerName(), target.getPrivateDnsServerName());
    }

    public boolean isIdenticalValidatedPrivateDnses(LinkProperties target) {
        Collection<InetAddress> targetDnses = target.getValidatedPrivateDnsServers();
        if (this.mValidatedPrivateDnses.size() == targetDnses.size()) {
            return this.mValidatedPrivateDnses.containsAll(targetDnses);
        }
        return false;
    }

    public boolean isIdenticalPcscfs(LinkProperties target) {
        Collection<InetAddress> targetPcscfs = target.getPcscfServers();
        if (this.mPcscfs.size() == targetPcscfs.size()) {
            return this.mPcscfs.containsAll(targetPcscfs);
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalRoutes(LinkProperties target) {
        Collection<RouteInfo> targetRoutes = target.getRoutes();
        if (this.mRoutes.size() == targetRoutes.size()) {
            return this.mRoutes.containsAll(targetRoutes);
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public boolean isIdenticalHttpProxy(LinkProperties target) {
        if (getHttpProxy() == null) {
            return target.getHttpProxy() == null;
        }
        return getHttpProxy().equals(target.getHttpProxy());
    }

    @UnsupportedAppUsage
    public boolean isIdenticalStackedLinks(LinkProperties target) {
        if (!this.mStackedLinks.keySet().equals(target.mStackedLinks.keySet())) {
            return false;
        }
        for (LinkProperties stacked : this.mStackedLinks.values()) {
            if (!stacked.equals(target.mStackedLinks.get(stacked.getInterfaceName()))) {
                return false;
            }
        }
        return true;
    }

    public boolean isIdenticalMtu(LinkProperties target) {
        return getMtu() == target.getMtu();
    }

    public boolean isIdenticalTcpBufferSizes(LinkProperties target) {
        return Objects.equals(this.mTcpBufferSizes, target.mTcpBufferSizes);
    }

    public boolean isIdenticalNat64Prefix(LinkProperties target) {
        return Objects.equals(this.mNat64Prefix, target.mNat64Prefix);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinkProperties)) {
            return false;
        }
        LinkProperties target = (LinkProperties) obj;
        if (!isIdenticalInterfaceName(target) || !isIdenticalAddresses(target) || !isIdenticalDnses(target) || !isIdenticalPrivateDns(target) || !isIdenticalValidatedPrivateDnses(target) || !isIdenticalPcscfs(target) || !isIdenticalRoutes(target) || !isIdenticalHttpProxy(target) || !isIdenticalStackedLinks(target) || !isIdenticalMtu(target) || !isIdenticalTcpBufferSizes(target) || !isIdenticalNat64Prefix(target)) {
            return false;
        }
        return true;
    }

    public CompareResult<LinkAddress> compareAddresses(LinkProperties target) {
        return new CompareResult<>(this.mLinkAddresses, target != null ? target.getLinkAddresses() : null);
    }

    public CompareResult<InetAddress> compareDnses(LinkProperties target) {
        return new CompareResult<>(this.mDnses, target != null ? target.getDnsServers() : null);
    }

    public CompareResult<InetAddress> compareValidatedPrivateDnses(LinkProperties target) {
        return new CompareResult<>(this.mValidatedPrivateDnses, target != null ? target.getValidatedPrivateDnsServers() : null);
    }

    public CompareResult<RouteInfo> compareAllRoutes(LinkProperties target) {
        return new CompareResult<>(getAllRoutes(), target != null ? target.getAllRoutes() : null);
    }

    public CompareResult<String> compareAllInterfaceNames(LinkProperties target) {
        return new CompareResult<>(getAllInterfaceNames(), target != null ? target.getAllInterfaceNames() : null);
    }

    public int hashCode() {
        int i;
        if (this.mIfaceName == null) {
            i = 0;
        } else {
            i = this.mIfaceName.hashCode() + (this.mLinkAddresses.size() * 31) + (this.mDnses.size() * 37) + (this.mValidatedPrivateDnses.size() * 61) + (this.mDomains == null ? 0 : this.mDomains.hashCode()) + (this.mRoutes.size() * 41) + (this.mHttpProxy == null ? 0 : this.mHttpProxy.hashCode()) + (this.mStackedLinks.hashCode() * 47);
        }
        return i + (this.mMtu * 51) + (this.mTcpBufferSizes == null ? 0 : this.mTcpBufferSizes.hashCode()) + (this.mUsePrivateDns ? 57 : 0) + (this.mPcscfs.size() * 67) + (this.mPrivateDnsServerName == null ? 0 : this.mPrivateDnsServerName.hashCode()) + Objects.hash(new Object[]{this.mNat64Prefix});
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getInterfaceName());
        dest.writeInt(this.mLinkAddresses.size());
        Iterator<LinkAddress> it = this.mLinkAddresses.iterator();
        while (it.hasNext()) {
            dest.writeParcelable(it.next(), flags);
        }
        dest.writeInt(this.mDnses.size());
        Iterator<InetAddress> it2 = this.mDnses.iterator();
        while (it2.hasNext()) {
            dest.writeByteArray(it2.next().getAddress());
        }
        dest.writeInt(this.mValidatedPrivateDnses.size());
        Iterator<InetAddress> it3 = this.mValidatedPrivateDnses.iterator();
        while (it3.hasNext()) {
            dest.writeByteArray(it3.next().getAddress());
        }
        dest.writeBoolean(this.mUsePrivateDns);
        dest.writeString(this.mPrivateDnsServerName);
        dest.writeInt(this.mPcscfs.size());
        Iterator<InetAddress> it4 = this.mPcscfs.iterator();
        while (it4.hasNext()) {
            dest.writeByteArray(it4.next().getAddress());
        }
        dest.writeString(this.mDomains);
        dest.writeInt(this.mMtu);
        dest.writeString(this.mTcpBufferSizes);
        dest.writeInt(this.mRoutes.size());
        Iterator<RouteInfo> it5 = this.mRoutes.iterator();
        while (it5.hasNext()) {
            dest.writeParcelable(it5.next(), flags);
        }
        if (this.mHttpProxy != null) {
            dest.writeByte((byte) 1);
            dest.writeParcelable(this.mHttpProxy, flags);
        } else {
            dest.writeByte((byte) 0);
        }
        dest.writeParcelable(this.mNat64Prefix, 0);
        dest.writeList(new ArrayList<>(this.mStackedLinks.values()));
    }

    public static boolean isValidMtu(int mtu, boolean ipv6) {
        if (ipv6) {
            if (mtu < 1280 || mtu > 10000) {
                return false;
            }
            return true;
        } else if (mtu < 68 || mtu > 10000) {
            return false;
        } else {
            return true;
        }
    }
}
