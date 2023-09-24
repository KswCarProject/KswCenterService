package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.shared.InetAddressUtils;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class StaticIpConfiguration implements Parcelable {
    public static final Parcelable.Creator<StaticIpConfiguration> CREATOR = new Parcelable.Creator<StaticIpConfiguration>() { // from class: android.net.StaticIpConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public StaticIpConfiguration createFromParcel(Parcel in) {
            return StaticIpConfiguration.readFromParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public StaticIpConfiguration[] newArray(int size) {
            return new StaticIpConfiguration[size];
        }
    };
    @UnsupportedAppUsage
    public final ArrayList<InetAddress> dnsServers;
    @UnsupportedAppUsage
    public String domains;
    @UnsupportedAppUsage
    public InetAddress gateway;
    @UnsupportedAppUsage
    public LinkAddress ipAddress;

    public StaticIpConfiguration() {
        this.dnsServers = new ArrayList<>();
    }

    public StaticIpConfiguration(StaticIpConfiguration source) {
        this();
        if (source != null) {
            this.ipAddress = source.ipAddress;
            this.gateway = source.gateway;
            this.dnsServers.addAll(source.dnsServers);
            this.domains = source.domains;
        }
    }

    public void clear() {
        this.ipAddress = null;
        this.gateway = null;
        this.dnsServers.clear();
        this.domains = null;
    }

    public LinkAddress getIpAddress() {
        return this.ipAddress;
    }

    public InetAddress getGateway() {
        return this.gateway;
    }

    public List<InetAddress> getDnsServers() {
        return this.dnsServers;
    }

    public String getDomains() {
        return this.domains;
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private Iterable<InetAddress> mDnsServers;
        private String mDomains;
        private InetAddress mGateway;
        private LinkAddress mIpAddress;

        public Builder setIpAddress(LinkAddress ipAddress) {
            this.mIpAddress = ipAddress;
            return this;
        }

        public Builder setGateway(InetAddress gateway) {
            this.mGateway = gateway;
            return this;
        }

        public Builder setDnsServers(Iterable<InetAddress> dnsServers) {
            this.mDnsServers = dnsServers;
            return this;
        }

        public Builder setDomains(String newDomains) {
            this.mDomains = newDomains;
            return this;
        }

        public StaticIpConfiguration build() {
            StaticIpConfiguration config = new StaticIpConfiguration();
            config.ipAddress = this.mIpAddress;
            config.gateway = this.mGateway;
            for (InetAddress server : this.mDnsServers) {
                config.dnsServers.add(server);
            }
            config.domains = this.mDomains;
            return config;
        }
    }

    public void addDnsServer(InetAddress server) {
        this.dnsServers.add(server);
    }

    public List<RouteInfo> getRoutes(String iface) {
        List<RouteInfo> routes = new ArrayList<>(3);
        if (this.ipAddress != null) {
            RouteInfo connectedRoute = new RouteInfo(this.ipAddress, (InetAddress) null, iface);
            routes.add(connectedRoute);
            if (this.gateway != null && !connectedRoute.matches(this.gateway)) {
                routes.add(RouteInfo.makeHostRoute(this.gateway, iface));
            }
        }
        if (this.gateway != null) {
            routes.add(new RouteInfo((IpPrefix) null, this.gateway, iface));
        }
        return routes;
    }

    public LinkProperties toLinkProperties(String iface) {
        LinkProperties lp = new LinkProperties();
        lp.setInterfaceName(iface);
        if (this.ipAddress != null) {
            lp.addLinkAddress(this.ipAddress);
        }
        for (RouteInfo route : getRoutes(iface)) {
            lp.addRoute(route);
        }
        Iterator<InetAddress> it = this.dnsServers.iterator();
        while (it.hasNext()) {
            InetAddress dns = it.next();
            lp.addDnsServer(dns);
        }
        lp.setDomains(this.domains);
        return lp;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("IP address ");
        if (this.ipAddress != null) {
            str.append(this.ipAddress);
            str.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        }
        str.append("Gateway ");
        if (this.gateway != null) {
            str.append(this.gateway.getHostAddress());
            str.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        }
        str.append(" DNS servers: [");
        Iterator<InetAddress> it = this.dnsServers.iterator();
        while (it.hasNext()) {
            InetAddress dnsServer = it.next();
            str.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            str.append(dnsServer.getHostAddress());
        }
        str.append(" ] Domains ");
        if (this.domains != null) {
            str.append(this.domains);
        }
        return str.toString();
    }

    public int hashCode() {
        int result = (13 * 47) + (this.ipAddress == null ? 0 : this.ipAddress.hashCode());
        return (((((result * 47) + (this.gateway == null ? 0 : this.gateway.hashCode())) * 47) + (this.domains != null ? this.domains.hashCode() : 0)) * 47) + this.dnsServers.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StaticIpConfiguration) {
            StaticIpConfiguration other = (StaticIpConfiguration) obj;
            return other != null && Objects.equals(this.ipAddress, other.ipAddress) && Objects.equals(this.gateway, other.gateway) && this.dnsServers.equals(other.dnsServers) && Objects.equals(this.domains, other.domains);
        }
        return false;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.ipAddress, flags);
        InetAddressUtils.parcelInetAddress(dest, this.gateway, flags);
        dest.writeInt(this.dnsServers.size());
        Iterator<InetAddress> it = this.dnsServers.iterator();
        while (it.hasNext()) {
            InetAddress dnsServer = it.next();
            InetAddressUtils.parcelInetAddress(dest, dnsServer, flags);
        }
        dest.writeString(this.domains);
    }

    public static StaticIpConfiguration readFromParcel(Parcel in) {
        StaticIpConfiguration s = new StaticIpConfiguration();
        s.ipAddress = (LinkAddress) in.readParcelable(null);
        s.gateway = InetAddressUtils.unparcelInetAddress(in);
        s.dnsServers.clear();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            s.dnsServers.add(InetAddressUtils.unparcelInetAddress(in));
        }
        s.domains = in.readString();
        return s;
    }
}
