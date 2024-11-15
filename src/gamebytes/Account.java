/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebytes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ADMIN
 */
public class Account implements Serializable {
    private String username;
    private char[] password; 
    public static HashSet<String> allUsernames = new HashSet<>();
    private HashMap<String, Integer> gameScores = new HashMap<>();

    public Account(String username, char[] password) throws Exception{
        if(!allUsernames.add(username))
            throw new Exception();
        
        this.username = username;
        this.password = password;
        this.gameScores = new HashMap<>();

        gameScores.put("Snek", 0);
        gameScores.put("WordGuessr", 0);

        System.out.println("Score for " + username + " for snek: " + gameScores.get("Snek"));
        System.out.println("Score for " + username + " for wordguessr: " + gameScores.get("WordGuessr"));
    }

    public void updateGameScore(String gameName, int score) {
        int currScore = gameScores.get(gameName);
        if (score > currScore) {
            gameScores.put(gameName, score);
        }

        System.out.println("Score for " + username + " for " + gameName + ": " + gameScores.get(gameName));
    }

    public int getGameScore(String gameName) {
        return gameScores.get(gameName);
    }

    public static List<ScoreEntry> getHighScores(String gameName, List<Account> accounts) {
        List<ScoreEntry> scores = new ArrayList<>();
        
        for (Account acc : accounts) {
            scores.add(new ScoreEntry(acc.getUsername(), acc.getGameScore(gameName)));
            System.out.println("High Score for " + acc.getUsername() + ": " + acc.getGameScore(gameName));
        }
        
        // Sort by score in descending order
        Collections.sort(scores, (a, b) -> b.score - a.score);
        
        return scores;
    }

    // class for simplifying handling of high scores
    public static class ScoreEntry {
        public String username;
        public int score;

        public ScoreEntry(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
    
    public static void printUsernames(){
        System.out.println("Username Count: " + allUsernames.size());
        System.out.println(allUsernames);
    }
}
