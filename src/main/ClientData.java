package main;

    public class ClientData {

                //Attribute
            private int clientID;
            private final int clientPort;
            private final boolean spectator;

                //Referenzen
            private final String clientIP;
            private final String username;


        public ClientData(String clientIP, int clientPort, String username, boolean spectator) {

            this.clientIP = clientIP;
            this.clientPort = clientPort;

            this.username = username;
            this.spectator = spectator;
        }

        public ClientData(String clientIP, int clientPort, String username, boolean spectator, int clientID) {

            this.clientIP = clientIP;
            this.clientPort = clientPort;

            this.clientID = clientID;
            this.username = username;
            this.spectator = spectator;
        }

        //--------------- GETTER ---------------\\

        public boolean isSpectator() {
            return spectator;
        }

        public String getUsername() {
            return username;
        }

        public int getClientPort() {

            return clientPort;
        }

        public int getClientID() {
            return clientID;
        }
    }
