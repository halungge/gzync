package org.mlz.sync.gzync.local.parser;

import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

public class ArgParser {

    private CliArguments args;
    private static Logger LOG;

    public ArgParser(CliArguments args) {
        this.args = args;
    }

    public ArgParser(){
        this.args = new CliArguments();
    }

    public void parse(String... input){
        if(input == null){
            new CommandLine(this.args).parse();
        }
        try {
            new CommandLine(this.args).parse(input);
        }
        catch(NullPointerException ex){

        }
        catch(CommandLine.UnmatchedArgumentException exp){
            throw new UnknownCliArgumentException("unknown cli argument", exp);
        }
    }

    public String getLocalFolder() {
        return args.localFolder;
    }
}
