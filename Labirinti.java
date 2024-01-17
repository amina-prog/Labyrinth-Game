import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Labirinti extends JFrame {

    private Labirint labirinti;
    private MazePanel mazePanel;
    private JLabel treasuresLabel;
    private boolean gameEnded;

    public Labirinti() {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labirinti = new Labirint();
                updateUI();
            }
        });

        JButton saveButton = new JButton("Save Game");
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (labirinti != null) {
                    try {
                        labirinti.ruajLojen(labirinti.getHarte(),"ruajtja.dat");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    showMessage("Game saved successfully!");
                } else {
                    showMessage("Start a new game first!");
                }
            }
        });

        JButton loadButton = new JButton("Load Game");
        loadButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (labirinti != null) {
                    try {
                        labirinti.ruajLojen(labirinti.getHarte(),"ruajtja.dat");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    updateUI();
                    showMessage("Game loaded successfully!");
                } else {
                    showMessage("Failed to load the game.");
                }
            }
        });

        mazePanel = new MazePanel();
        treasuresLabel = new JLabel("Thesare: 0");
        add(treasuresLabel, BorderLayout.NORTH);

        setTitle("Loja e Labirintit");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton levizDjathtasButton = new JButton("Djathtas");
        JButton levizMajtasButton = new JButton("Majtas");
        JButton levizLartButton = new JButton("Lart");
        JButton levizPoshteButton = new JButton("Poshte");
        JButton exitButton = new JButton("Exit");
        levizDjathtasButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        levizMajtasButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        levizLartButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        levizPoshteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        exitButton.setFont(new Font("Times New Roman", Font.BOLD, 14));


        levizDjathtasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("R");
            }
        });

        levizMajtasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("L");
            }
        });

        levizLartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("U");
            }
        });

        levizPoshteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("D");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.add(levizMajtasButton);
        buttonPanel.add(levizDjathtasButton);
        buttonPanel.add(startGameButton);
        buttonPanel.add(levizLartButton);
        buttonPanel.add(levizPoshteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(mazePanel, BorderLayout.CENTER);

        updateUI();
    }


    private void handleMovement(String direction) {
        if (!gameEnded) {
            if (labirinti.kontrollPerMur(direction)) {
                if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == '*') {
                    labirinti.nrThesareveTeMbledhura++;
                    treasuresLabel.setText("Thesare: " + labirinti.nrThesareveTeMbledhura);
                    showMessage("Ti ke mbledhur " + labirinti.nrThesareveTeMbledhura + " thesare.");
                    perfundoLojen();
                } else if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == '0') {
                    showMessage("Urime arrite ne dalje!");
                    perfundoLojen();
                } else {
                    labirinti.levizLojtar(direction);
                    updateUI();
                    kontrolloStatusinLojes();
                }
            } else {
                showMessage("Godite nje mur! Game Over");
                perfundoLojen();
            }
        }}
    private void kontrolloStatusinLojes() {
        if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == labirinti.getHarte()[labirinti.daljeRresht][labirinti.daljeKolone]) {
            JOptionPane.showMessageDialog(null, "Urime arrite ne dalje!");
            perfundoLojen();
        }

    }


    private void perfundoLojen() {
        gameEnded = true;
        System.exit(0);
    }

    protected Labirint loadLojen() {
        try (ObjectInputStream ngarkim = new ObjectInputStream(new FileInputStream("labirinti.dat"))) {
            int rreshta = 1;
            int kolona = 0;
            char[][] labirintArray = (char[][]) ngarkim.readObject();


            return new Labirint(labirinti.getRreshta(), labirinti.getKolona(), labirintArray);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void updateUI() {
        if (labirinti != null) {
            mazePanel.repaint();
            treasuresLabel.setText("Thesare: " + labirinti.nrThesareveTeMbledhura);
            repaint();
        }
    }


    private class MazePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (labirinti != null) {
                char[][] map = labirinti.getHarte();
                int madhesiaQelizave = 40;

                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        ImageIcon icon = null;
                        if (map[i][j] == '|') {
                            icon = new ImageIcon("C:\\Users\\35568\\Downloads\\mur.jpg");
                        } else if (map[i][j] == 'X') {
                            icon = new ImageIcon("C:\\Users\\35568\\Downloads\\lojtar .jpg");
                        } else if (map[i][j] == '0') {
                            icon = new ImageIcon("C:\\Users\\35568\\Downloads\\dalje.jpg");
                        } else if (map[i][j] == '*') {
                            icon = new ImageIcon("C:\\Users\\35568\\Downloads\\thesar.jpg");
                        }
                        if (icon != null) {
                            Image image = icon.getImage();
                            g.drawImage(image, j * madhesiaQelizave, i * madhesiaQelizave, madhesiaQelizave, madhesiaQelizave, this);
                        } else {
                            g.setColor(Color.WHITE);
                            g.fillRect(j * madhesiaQelizave, i * madhesiaQelizave, madhesiaQelizave, madhesiaQelizave);
                        }



                        g.drawRect(j * madhesiaQelizave, i * madhesiaQelizave, madhesiaQelizave, madhesiaQelizave);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Labirinti().setVisible(true);
            }
        });

    }


}
