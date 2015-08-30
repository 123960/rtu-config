# rtu-config

  Creates a config ready-to-up to ACM based on a config downloaded from ACM replacing the name of the files with the correct name
  and creating an installation.xml file.

## Installation

  Just download the jar file.

## Usage

  $ java -jar rtu-config-0.1.0-standalone.jar [config-path config-name config-desc]

  config-path = Path to directory with the configuration downloaded from ACM
  config-name = Name of the configuration in the installation.xml file (tag <ns3:name>)
  config-desc = Description of the configuration in the installation.xml file (tag <ns3:description>)

## Options

  None option has been implemented yet

## Examples

  $ java -jar rtu-config-0.1.0-standalone.jar ""/home/acm-configproperties/14368173677051" "VINI-CONFIG" "VINI-DESC"

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2015