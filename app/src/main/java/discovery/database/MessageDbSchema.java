package discovery.database;

/**
 * Created by Noelia on 13/04/2016.
 */
public class MessageDbSchema {

    public static final class MessageTable{
        public static final String NAME = "messages";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String MSG = "msg";
            public static final String SENDER = "sender";
            public static final String TIME = "time";
            public static final String TTL = "ttl";
            public static final String PATH = "path";


        }
    }
    public static final class ScanTable{
        public static final String NAME = "scans";

        public static final class Cols {
            public static final String ID = "id";
            public static final String SCAN_INIT = "scan_init";
            public static final String SCAN_END = "scan_end";
            public static final String SCAN_DURATION = "scan_duration";
            public static final String PEERS = "peers";

        }
    }
    public static final class ConnectionTable{
        public static final String NAME = "connections";

        public static final class Cols {
            public static final String ID = "id";
            public static final String GO = "go";
            public static final String INITIATOR = "initiator";
            public static final String FAILED = "failed";
            public static final String FAILURE = "failure";
            public static final String CONNECTED_PEER = "connected_peer";
            public static final String CONNECTING = "connecting";
            public static final String CONNECTION_INIT = "connection_init";
            public static final String DISCONNECTING = "disconnecting";
            public static final String CONNECTION_END = "connection_end";
            public static final String CONNECTION_MESSAGES = "connection_messages";

        }
    }
    public static final class GeoTable{
        public static final String NAME = "geo";

        public static final class Cols {
            public static final String ACTION = "action";
            public static final String TIME = "time";
        }
    }

    public static final class BatteryTable{
        public static final String NAME = "battery";

        public static final class Cols {
            public static final String PERCENTAGE = "percentage";
            public static final String TIME = "time";
        }
    }
}
