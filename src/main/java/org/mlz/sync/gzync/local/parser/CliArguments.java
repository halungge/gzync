package org.mlz.sync.gzync.local.parser;

import picocli.CommandLine.Option;

public class CliArguments {

    @Option(names={"-l", "--localPath"}, description="local root directory to sync (default: ${DEFAULT_VALUE} )")
    String localFolder = ".";


    // only sync this sub folder
    private String remoteRootFolder;

    // other options:

    // verbose
    // logging

    // dryrun

    // ???
}
