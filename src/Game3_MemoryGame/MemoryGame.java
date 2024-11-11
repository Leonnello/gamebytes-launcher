package Game3_MemoryGame;
/**
 *
 * @author Jack
 */

// !!!!
// !!!!!!
// !!!!!!!!
// import the package and build in main menu with: new memorygame()
// !!!!!!!!
// !!!!!!
// !!!!

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import gamebytes.Launcher;

public class MemoryGame extends JFrame implements ActionListener {
    // object declaration
    private JPanel startPanel, gamePanel, grid;
    private JMenuBar menuBar;
    private JMenu difficultyMenu, controlMenu, exit, timerDifficulty, gridDifficulty;
    private JMenuItem exitConfirm, newGame, timerEasy, timerMedium, timerHard, gridEasy, gridMedium, gridHard;
    private int gridSize;
    private JButton[][] buttons;
    private JButton firstCard, secondCard, startButton;
    private boolean checkingMatch = false, gameStarted;
    private Icon faceDown;
    private Icon[][] cardIcons;
    private ArrayList<Icon> icons;
    private JLabel title;
    private Timer gameTimer;
    private int timeRemaining, matchedPairs;
    private JLabel timerLabel;
    private String timeDiff;
    
    // Constructor
    public MemoryGame(Launcher launcher) {
        
        
        // components
            // menu bar
            menuBar = new JMenuBar();

            // difficulty menu
            difficultyMenu = new JMenu("Difficulty");
                // difficulty submenus
                timerDifficulty = new JMenu("Timer");
                gridDifficulty = new JMenu("Grid");

                timerEasy = new JMenuItem("Easy");
                timerMedium = new JMenuItem("Medium");
                timerHard = new JMenuItem("Hard");

                gridEasy = new JMenuItem("Easy");
                gridMedium = new JMenuItem("Medium");
                gridHard = new JMenuItem("Hard");

                timerDifficulty.add(timerEasy);
                timerDifficulty.add(timerMedium);
                timerDifficulty.add(timerHard);
                gridDifficulty.add(gridEasy);
                gridDifficulty.add(gridMedium);
                gridDifficulty.add(gridHard);
                
                difficultyMenu.add(timerDifficulty);
                difficultyMenu.add(gridDifficulty);
            
            // Control menu
            controlMenu = new JMenu("Game Control");

                newGame = new JMenuItem("Start New Game with Current Settings");

                controlMenu.add(newGame);

            // Exit menu
            exit = new JMenu("Exit Game");
            
                exitConfirm = new JMenuItem("Are you sure?");

                exit.add(exitConfirm);
            
            // Adding the menus to the frame
            menuBar.add(difficultyMenu);
            menuBar.add(controlMenu);
            menuBar.add(exit);
                       
            setJMenuBar(menuBar);
        
            // The title
            title = new JLabel("Memory Game", SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD , 30));
        
            // First screen        
                // Instruction display
                JPanel instruction = new JPanel();
                JLabel instructionLabel = new JLabel("Instructions:", SwingConstants.CENTER);
                Font header = new Font("Arial", 0, 20);
                instructionLabel.setFont(header);
                JLabel instructions1 = new JLabel("When you start the game, you will be faced with a grid of cards.", SwingConstants.CENTER);
                JLabel instructions2 = new JLabel("Click the \"Start\" button to hide the grid's cards and start the game at any time.\n", SwingConstants.CENTER);
                JLabel instructions3 = new JLabel("When the game starts, you have until the time limit to match all of the now hidden cards!", SwingConstants.CENTER);
                
                instruction.setLayout(new GridLayout(0, 1));
                instruction.add(instructionLabel);
                instruction.add(instructions1);
                instruction.add(instructions2);
                instruction.add(instructions3);
                
                // Notes to user
                JPanel note = new JPanel();
                JLabel noteLabel = new JLabel("Note:", SwingConstants.CENTER);
                noteLabel.setFont(header);
                JLabel notes1 = new JLabel("You can change the size of the grid, how much time you have through the menu in \"Difficulty\".", SwingConstants.CENTER);
                JLabel notes2 = new JLabel("Start a new game with set settings (Default: Easy) via the \"Game Control\" menu.", SwingConstants.CENTER);
                
                note.setLayout(new GridLayout(0, 1));
                note.add(noteLabel);
                note.add(notes1);
                note.add(notes2);
            
                // Panel to keep the text part of the starting panel together
                JPanel startTextPanel = new JPanel();

                startTextPanel.setLayout(new BorderLayout());
                startTextPanel.add(instruction, BorderLayout.NORTH);
                // Visual seperator
                startTextPanel.add(new JSeparator());
                startTextPanel.add(note, BorderLayout.SOUTH);

                // Preview picture of the game
                JLabel previewPic = new JLabel(new ImageIcon(getClass().getResource("/Game3_MemoryGame/images/previewPic.png")));
                previewPic.setPreferredSize(new Dimension(250, 200));
                JLabel author = new JLabel("Created By: Jack Taylor");
                JPanel preview = new JPanel();
                preview.setLayout(new BorderLayout());
                preview.add(previewPic, BorderLayout.CENTER);
                preview.add(author, BorderLayout.SOUTH);
                
            
                // Bring it all together for a panel for the first screen
                startPanel = new JPanel();
                // Adding the two panels to the start panel screen
                startPanel.setLayout(new BorderLayout());
                startPanel.add(title, BorderLayout.NORTH);
                startPanel.add(startTextPanel, BorderLayout.CENTER);
                startPanel.add(preview, BorderLayout.SOUTH);
                startPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Second screen, grid handled by createGame
                gamePanel = new JPanel();
                gamePanel.setLayout(new BorderLayout());               
        
        
        // Frame settings
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Add the starting panel to the frame at initialization
        add(startPanel);
        
        // Sets default difficulties
        gridDifficulty("Easy");
        timeDiff = "Easy";
        timerDifficulty(timeDiff);
        
        // Initializes event listeners
        initListeners();
        
        // Resizes frame
        pack();
        
        // Centers the initialized window to the middle of the screen
        setLocationRelativeTo(null);
        
        
        //go back to main menu when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                launcher.setVisible(true);
                System.out.println("game2 closed.");
            }
        });
        
        // Displays the finalized JFrame
        setResizable(false);
        setVisible(true);
    }
    
    // Adds listeners to menu objects after the frame initializes
    private void initListeners() {
        // listeners for menu
        newGame.addActionListener(this);
        exitConfirm.addActionListener(this);
        timerEasy.addActionListener(this);
        timerMedium.addActionListener(this);
        timerHard.addActionListener(this);
        gridEasy.addActionListener(this);
        gridMedium.addActionListener(this);
        gridHard.addActionListener(this);
    }
    
    // Handles game creation logic
    private void createGame() {
        // Resetting important variables
        timerDifficulty(timeDiff);
        gameStarted = false;
        matchedPairs = 0;
        if(gameTimer != null) {
            gameTimer.stop();
        }
        if(grid != null) {
            grid.removeAll();
        }
        // Grid panel using variable grid sizes to accomidate different grid-based difficulties
        grid = new JPanel(new GridLayout(gridSize, gridSize));
        
        buttons = new JButton[gridSize][gridSize];
        
        // Load images
        loadImages();
        
        // Creates and adds buttons to the grid according to gridSize
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setIcon(cardIcons[row][col]);
                // Separate event listener as buttons are generated at gamestart
                buttons[row][col].addActionListener(new CardClickListener(row, col));
                grid.add(buttons[row][col]);
            }
        }
        
        gamePanel.removeAll();
        
        // Button to start the game.
        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> hideCardsAndStartGame());
        
        // Timer display at the top of the game panel
        timerLabel = new JLabel("Time Left: " + timeRemaining + " seconds", SwingConstants.CENTER);
        gamePanel.add(timerLabel, BorderLayout.NORTH);
        
        JPanel titleAndTimer = new JPanel(new BorderLayout());
        titleAndTimer.add(title, BorderLayout.NORTH);
        titleAndTimer.add(timerLabel, BorderLayout.CENTER);
        
        gamePanel.add(titleAndTimer, BorderLayout.NORTH);
        gamePanel.add(grid, BorderLayout.CENTER);
        gamePanel.add(startButton, BorderLayout.SOUTH);
        
        // Switch panels from starting to gamestate
        remove(startPanel);
        add(gamePanel);
        
        // Refresh jframe
        gamePanel.revalidate();
        gamePanel.repaint();
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
    }
    
    private void loadImages() {
        // Load an image for cards facedown
        faceDown = new ImageIcon("/Game3_MemoryGame/images/facedown.png");

        // Load face-up images and assign each pair an icon
        icons = new ArrayList<>();
        for (int i = 1; i <= (gridSize * gridSize) / 2; i++) {
            Icon icon = new ImageIcon(getClass().getResource("/Game3_MemoryGame/images/" + i + ".png")); // Adjust path as needed
            icons.add(icon);
            icons.add(icon); // Add each icon twice for pairs
        }
        shuffleCards();
    }
        
    private void shuffleCards() {
        
        
        Collections.shuffle(icons);
        
        // Assign shuffled icons to the cardIcons grid
        cardIcons = new Icon[gridSize][gridSize];
        int index = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cardIcons[row][col] = icons.get(index++);
            }
        }
    }
    
    // Button action listener
    private void hideCardsAndStartGame() {
        if (!gameStarted) {
            // Hide all cards by setting them to face-down icon
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    buttons[row][col].setIcon(faceDown);
                }
            }
            gameStarted = true; // Game officially starts after hiding cards
            startButton.setEnabled(false);
            startTimer();
        }
    }

    
    
    // Separate ActionListener for card clicks
    private class CardClickListener implements ActionListener {
        private int row;
        private int col;
        
        public CardClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        // Card checking logic
        @Override
        public void actionPerformed(ActionEvent e) {
            // Ignore if game hasn't started, already revealed or checking a match
            if (!gameStarted || checkingMatch || buttons[row][col].getIcon() == cardIcons[row][col]) {
                return;
            }
            
            buttons[row][col].setIcon(cardIcons[row][col]);
            
            if (firstCard == null) {
                firstCard = buttons[row][col];
            } else {
                secondCard = buttons[row][col];
                checkingMatch = true;

                // Delay to allow player to see both cards
                Timer timer = new Timer(600, evt -> checkMatch());
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
    
    private void checkMatch() {
        if (firstCard.getIcon() == secondCard.getIcon()) {
            // It's a match, keep both cards revealed
            firstCard.setEnabled(false);
            secondCard.setEnabled(false);
            System.out.println("Matched a pair!");
            matchedPairs++;
            
            if (matchedPairs == ((gridSize * gridSize) / 2)) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "You matched all the cards!\nYou won!\nUse \"Start New Game\" again to try again!");
            }
            
        } else {
            // Not a match, flip them back over
            firstCard.setIcon(faceDown);
            secondCard.setIcon(faceDown);
        }

        // Reset for the next turn
        firstCard = null;
        secondCard = null;
        checkingMatch = false;
    }
    
    private void gridDifficulty(String difficulty) {
        switch(difficulty) {
            case "Medium":
                gridSize = 6;
                break;
            case "Hard":
                gridSize = 8;
                break;
            default:
                gridSize = 4;
                break;
        }
    }
    
    private void timerDifficulty(String difficulty) {
        switch(difficulty) {
            case "Medium":
                timeRemaining = 60;
                break;
            case "Hard":
                timeRemaining = 30;
                break;
            default:
                timeRemaining = 120;
                break;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        
        // handles exiting
        if (src == exitConfirm) {
            // closes and erases the JFrame object instead of the entire application
            dispose();
        }
        
        // handles creating a new game
        if (src == newGame) {
            createGame();
        }
        
        // Handles grid difficulty
        if (src == gridEasy) {
            gridDifficulty("Easy");
            createGame();
        }
        if (src == gridMedium) {
            gridDifficulty("Medium");
            createGame();
        }
        if (src == gridHard) {
            gridDifficulty("Hard");
            createGame();
        }
        
        // Handles timer difficulty
        if (src == timerEasy) {
            if (!gameStarted) {
                timeDiff = "Easy";
                timerDifficulty(timeDiff);
                timerLabel.setText("Time Left: " + timeRemaining + " seconds");
            }
        }
        if (src == timerMedium) {
            if (!gameStarted) {
                timeDiff = "Medium";
                timerDifficulty(timeDiff);
                timerLabel.setText("Time Left: " + timeRemaining + " seconds");
            }
        }
        if (src == timerHard) {
            if (!gameStarted) {
                timeDiff = "Hard";
                timerDifficulty(timeDiff);
                timerLabel.setText("Time Left: " + timeRemaining + " seconds");
            }
        }
    }
    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            timeRemaining--;
            timerLabel.setText("Time Left: " + timeRemaining + " seconds");
            
            if (timeRemaining <= 0) {
                gameTimer.stop();
                gameStarted = false;
                JOptionPane.showMessageDialog(null, "Time's up!\nYou lost!\nUse \"Start New Game\" again to try again!");
            }
        });
        gameTimer.start();
    }
//    
//    public static void main(String[] args) {
//        new MemoryGame();
//    }
}
