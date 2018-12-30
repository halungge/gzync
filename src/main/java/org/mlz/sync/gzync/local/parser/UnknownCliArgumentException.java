package org.mlz.sync.gzync.local.parser;

import picocli.CommandLine;

import java.util.List;


public class UnknownCliArgumentException extends RuntimeException{
    private List<String> suggestions;
    private String cmdLine;

    public UnknownCliArgumentException(String message, CommandLine.UnmatchedArgumentException e){
        super("unmatched argument: " + e.getUnmatched(), e);
        this.suggestions = e.getSuggestions();
        this.cmdLine = e.getCommandLine().toString();


    }
}
