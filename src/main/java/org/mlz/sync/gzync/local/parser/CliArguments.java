package org.mlz.sync.gzync.local.parser;

import picocli.CommandLine.Option;

// TODO make this private internal class of the parser
class CliArguments {


    String remoteFolder = "/";

    @Option(names={"-l", "--localPath"}, description="local root directory to sync (default: ${DEFAULT_VALUE} )")
    String localFolder = ".";

    @Option(names={"-f", "--force-new"}, description="force a new download of all files from the remote google drive")
    boolean forcedDownload = false;




    // other options:

    // verbose
    // logging

    // dryrun

    // ???
}
