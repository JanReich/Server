package main;

import abitur.netz.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class GameServer extends Server {

                //Attribute
            private int playerCount;
            private final int minPlayer;
            private final int maxPlayer;
            private final boolean spectatingAllowed;

                //Game-Config
            private boolean started;


                //ClientsIDs
            private String clientID1 = null;
            private String clientID2 = null;
            private String clientID3 = null;

                //Referenzen
            private List<String> names;
            private Map<String, ClientData> clients;

            private Serverpanel panel;

        public GameServer(Serverpanel panel, int pPort, int minPlayer, int maxPlayer, boolean spectatingAllowed) {

            super(pPort);

            this.panel = panel;
            names = new ArrayList<>();
            clients = new HashMap<>();

            this.minPlayer = minPlayer;
            this.maxPlayer = maxPlayer;
            this.spectatingAllowed = spectatingAllowed;
        }

        @Override
        public void processNewConnection(String pClientIP, int pClientPort) {

            if(!clients.containsKey(pClientIP)) clients.put(pClientIP, null);

            panel.updateClients(clients);
        }

        @Override
        public void processMessage(String pClientIP, int pClientPort, String pMessage) {

                //Register Client
            if(pMessage.startsWith("RegisterClient: ")) {

                if(clients.get(pClientIP) == null) {

                    String[] substrings = pMessage.split(": ");
                    String username = substrings[2].replace(", spectator", "");
                    boolean spectator = Boolean.parseBoolean(substrings[3]);

                    if(!names.contains(username)) {

                        if(!(!spectatingAllowed && spectator)) {

                            if(!started) {

                                    //Senden der Player-Daten von den Spielern die bisher schon connected sind
                                if(clients.size() != 1) {

                                    String message = "ClientData: ";

                                    for (Map.Entry<String, ClientData> entry : clients.entrySet()) {

                                        if(!(entry.getKey() == pClientIP) && entry.getValue() != null) {

                                            message += "clientID:" + entry.getValue().getClientID() + ":";
                                            message += "username:" + entry.getValue().getUsername() + ":";
                                        } else continue;
                                    }
                                    send(pClientIP, pClientPort, message);
                                }

                                //Wenn der Spieler als Spectator joint
                                if (spectator) {

                                    names.add(username);
                                    send(pClientIP, pClientPort, "RegisterSuccessful: ");
                                    clients.put(pClientIP, new ClientData(pClientIP, pClientPort, username, spectator));
                                    System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spectator verbunden!");
                                } else {

                                    if (playerCount < maxPlayer) {

                                        int clientID = 0;

                                        if (clientID1 == null) {

                                            clientID = 1;
                                            clientID1 = pClientIP;
                                        } else if (clientID2 == null) {

                                            clientID = 2;
                                            clientID2 = pClientIP;
                                        } else if (clientID3 == null) {

                                            clientID = 3;
                                            clientID3 = pClientIP;
                                        }

                                        sendToAll("NewPlayer: clientID:" + clientID + ":username:" + username);

                                        playerCount++;
                                        names.add(username);
                                        send(pClientIP, pClientPort, "RegisterSuccessful: " + clientID);
                                        clients.put(pClientIP, new ClientData(pClientIP, pClientPort, username, spectator, clientID));
                                        System.out.println("[Server] Client \"" + username + "\" hat sich mit dem Server als Spieler verbunden!");
                                    } else send(pClientIP, pClientPort,"Disconnect: Server full");
                                }
                            } else send(pClientIP, pClientPort, "Disconnect: Game Already started");
                        } else send(pClientIP, pClientPort, "Disconnect: No Spectators allowed");
                    } else send(pClientIP, pClientPort, "Disconnect: Username Already in use");
                } else send(pClientIP, pClientPort, "Disconnect: A Client of this Device is already connected to the Server");
            }

            else if(pMessage.startsWith("Ready: ")) {


            }

            panel.updateClients(clients);
        }

        @Override
        public void processClosingConnection(String pClientIP, int pClientPort) {

            if(clients != null) {

                if(clients.containsKey(pClientIP) && clients.get(pClientIP) != null) {

                    if (clients.get(pClientIP).getClientPort() == pClientPort) {

                        if (!clients.get(pClientIP).isSpectator()) {

                            playerCount--;

                            if (pClientIP.equals(clientID1)) clientID1 = null;
                            if (pClientIP.equals(clientID2)) clientID2 = null;
                            if (pClientIP.equals(clientID3)) clientID3 = null;
                        }
                        names.remove(clients.get(pClientIP).getUsername());
                        System.out.println("[Server] Client \"" + clients.get(pClientIP).getUsername() + "\" hat die Verbindung zum Server getrennt!");
                        clients.remove(pClientIP);
                    }
                }
            }

            panel.updateClients(clients);
        }
    }
