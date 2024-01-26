package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class makes countdown timer app
 */

public class CDTimer extends JFrame{

    //timer placement
    private JLabel countdownLabel;

    //panel for buttons
    private JPanel countdownPanel;

    //time: kept just in seconds
    private int seconds;

    //actual timer

    private Timer timer;

    //check for timer start
    private boolean startButtonPressed = false;

    public CDTimer(){
        initializeUI();
    }


    /**
     * the body of the countdown timer
     */

    private void initializeUI() {

        frameDesign();

        Dimension screenSize = getDimension();

        JLabel timerLabel = getjLabel(screenSize);
        
        seconds = secondsCalculator();

        //in case of pressing reset button
        int backupSeconds = seconds;

        countdownLabel();
        
        countdownPanel(screenSize, backupSeconds);

        JPanel northPanel = getNorthPanel(timerLabel);

        JPanel southPanel = getSouthPanel();

        add(northPanel, BorderLayout.NORTH);

        add(southPanel, BorderLayout.SOUTH);

        add(countdownLabel, BorderLayout.CENTER);
    }


    /**
     * south panel with its design
     */
    private JPanel getSouthPanel() {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(Color.BLACK);
        southPanel.add(countdownPanel);
        return southPanel;
    }


    /**
     * north panel with its design
     */

    private static JPanel getNorthPanel(JLabel timerLabel) {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setBackground(Color.BLACK);
        northPanel.add(timerLabel);
        return northPanel;
    }

    /**
     * frame with its design
     */
    private void frameDesign() {
        setTitle("Countdown Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
    }


    //getting dimensions of the screen
    private Dimension getDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        return screenSize;
    }


    /**
     * creating the timers central label
     */
    private void countdownLabel() {
        countdownLabel = new JLabel(
                String.format("%02d:%02d:%02d",0, 0, 0));

        countdownLabel.setFont(new Font("Calibri", Font.BOLD, 130));
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        countdownLabel.setBackground(Color.PINK);
    }


    /**
     * creating the panel with buttons
     */
    private void countdownPanel(Dimension screenSize, int backupSeconds) {
        countdownPanel = new JPanel();
        countdownPanel.setBackground(Color.BLACK);
        countdownPanel.setSize(screenSize.width, screenSize.height);

        JButton resetButton = new JButton("Reset");
        JButton pauseButton = new JButton("Pause");
        JButton startButton = new JButton("Start");

        countdownPanel.add(resetButton);
        countdownPanel.add(startButton);
        countdownPanel.add(pauseButton);


        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds = backupSeconds;
                countdownLabel.setText(formatTime());
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButtonPressed){
                    timer.stop();
                    startButtonPressed = false;
                }
            }
        });


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!startButtonPressed) {
                    countdownLabel.setText(formatTime());
                    timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            updateTimer();
                        }
                    });
                    timer.start();
                    startButtonPressed = true;
                }
            }
        });
    }

    /**
     * getting label with the name of the timer by user choice
     * @param screenSize screen dimensions for creating north label
     * @return label
     */

    private static JLabel getjLabel(Dimension screenSize) {
        String label = JOptionPane.showInputDialog(null, "Enter the timer label:");
        if(label == null)
            System.exit(0);

        if (label.equals("")) {
            label = "Timer";
        }
        JLabel timerLabel = new JLabel(label);

        timerLabel.setFont(new Font("Calibri", Font.BOLD, 50));
        timerLabel.setForeground(Color.LIGHT_GRAY);
        timerLabel.setSize(screenSize.width, screenSize.height / 6);
        timerLabel.setBackground(Color.DARK_GRAY);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return timerLabel;
    }


    /**
     * getting and calculating the seconds
     * @return the time in seconds
     */

    private static int secondsCalculator() {
        String hourString = getInputTime("Enter the hours:", "Enter the hours(digits):");
        String minString = getInputTime("Enter the minutes:", "Enter the minutes(digits):");
        String secondString = getInputTime("Enter the seconds:", "Enter the seconds(digits):");
        return Integer.parseInt(secondString)+ Integer.parseInt(minString)*60+Integer.parseInt(hourString)*3600;
    }


    private static String getInputTime(String message, String message1) {
        String hourString = JOptionPane.showInputDialog(null, message);
        if (hourString == null)
            System.exit(0);
        while (!hourString.matches("\\d+")) {
            hourString = JOptionPane.showInputDialog(null, message1);
        }
        return hourString;
    }

    /**
     * updating the time, starting the music and timer
     */
    private void updateTimer() {
        Thread musicThread = new Thread(new MusicPlayer());
        seconds--;
        if(seconds == 3){
            musicThread.start();
        }
        if(seconds <= 3 && seconds>=0){
            countdownLabel.setForeground(Color.RED);
            countdownLabel.setText(formatTime());
        }else if (seconds >= 0) {
            if(musicThread.isAlive()){
                musicThread.interrupt();
            }
            countdownLabel.setForeground(Color.BLACK);
            countdownLabel.setText(formatTime());
        } else {
            countdownLabel.setText("Time's up!");
            timer.stop();
        }
    }

    /**
     * formatting the time
     * @return formatted time
     */

    private String formatTime() {
        int hours = seconds / 3600;
        int minutes = (seconds - (hours*3600))/60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d",hours, minutes, remainingSeconds);
    }
}


