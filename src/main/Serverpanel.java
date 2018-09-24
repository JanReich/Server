package main;

import config.ServerConfig;
import graphics.Display;
import graphics.interfaces.BasicInteractableObject;
import toolBox.DrawHelper;
import toolBox.Inputmanager;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;

    public class Serverpanel implements BasicInteractableObject {

                //Attribute
            private final int port;
            private final int minPlayer;
            private final int maxPlayer;
            private final boolean spectatorJoin;

                //Referenzen
            private Display display;
            private ServerConfig config;
            private Map<String, ClientData> clients;

        public Serverpanel(Display display) {

            this.display = display;

            config = new ServerConfig();
            port = config.getServerPort();
            minPlayer = config.getMinPlayers();
            maxPlayer = config.getMaxPlayers();
            spectatorJoin = config.isSpectatorJoin();

        }

        @Override
        public void keyPressed(KeyEvent event) {

        }

        @Override
        public void keyReleased(KeyEvent event) {

        }

        @Override
        public void mouseReleased(MouseEvent event) {

                //Server starten
            if(Inputmanager.isInside(event, 100, 100, 300, 75)) {

                startServer();
            }
        }

        @Override
        public void update(double delta) {

        }

        @Override
        public void draw(DrawHelper draw) {

            draw.drawRec(100, 100, 300, 75);
            draw.setFont(draw.createFont(1, 25));
            draw.drawString("Server starten", 150, 150);

                //Kickpannel
            if(clients != null) {

                int i = 0;

                for (Map.Entry<String, ClientData> entry : clients.entrySet()) {

                    i++;
                    ClientData data = entry.getValue();
                    if(data != null) {

                        draw.drawString(data.getUsername(), 150, 280 + 100 * i);
                        draw.drawRec(650, 270 + 100 * i, 20);
                        draw.drawLine(100, 300 + 100 * i, 800, 300 + 100 * i);
                    }
                }

                i = 0;
            }
        }

        /**
         * In dieser Methode wird der Server gestartet
         */
        private void startServer() {

            System.out.println("[Server] Versuche einen Server auf dem Port " + port + "  zu starten...");
            new GameServer(this, port, minPlayer, maxPlayer, spectatorJoin);
        }

        public void updateClients(Map<String, ClientData> clients) {

            this.clients = clients;
        }
    }
