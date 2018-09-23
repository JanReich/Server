package config;

import toolBox.FileHelper;

import java.io.File;
import java.util.HashMap;

    public class ServerConfig extends Config {

                //Attribute
            private int serverPort;

            private int minPlayers;
            private int maxPlayers;

            private boolean spectatorJoin;

                //Referenzen


        public ServerConfig() {

            super(new File("res/configs/Server.properties"));
        }

        @Override
        public void readConfig() {

            serverPort = Integer.parseInt(FileHelper.getProperty(file, "port"));

            minPlayers = Integer.parseInt(FileHelper.getProperty(file, "Min-Player"));
            maxPlayers = Integer.parseInt(FileHelper.getProperty(file, "Max-Player"));

            if(FileHelper.getProperty(file, "Spectator-Join").equalsIgnoreCase("allowed")) spectatorJoin = true;
            else spectatorJoin = false;

        }

        @Override
        public void setStandards() {

            if(!FileHelper.isFileExisting(file)) {

                FileHelper.createFile(file);

                HashMap<String, String> config = new HashMap<>();
                config.put("port", "666");

                config.put("MinPort", "2");
                config.put("MaxPort", "2");

                config.put("Spectator-Join", "denied");

                FileHelper.setProperty(file, config);
            }
        }

        @Override
        public void save() {

        }


        //------------------- GETTER AND SETTER ------------------- \\

        public int getServerPort() {

            return serverPort;
        }

        public void setServerPort(int serverPort) {

            this.serverPort = serverPort;
        }

        public int getMinPlayers() {

            return minPlayers;
        }

        public void setMinPlayers(int minPlayers) {

            this.minPlayers = minPlayers;
        }

        public int getMaxPlayers() {

            return maxPlayers;
        }

        public void setMaxPlayers(int maxPlayers) {

            this.maxPlayers = maxPlayers;
        }

        public boolean isSpectatorJoin() {

            return spectatorJoin;
        }

        public void setSpectatorJoin(boolean spectatorJoin) {

            this.spectatorJoin = spectatorJoin;
        }
    }
