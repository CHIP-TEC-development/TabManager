# TabManager
A plugin used to create custom TAB lists for commands that do not support them.

## What is a TAB list?
A tab list or an executor is called whenever someone presses the LIST PLAYERS button on a command that supports
tab executors. If there is no TabExecutor, pressing the LIST PLAYERS button will simply do that: give a list of players.
  
  Tab Executors can be specific to permission, player, argument length, world, etc.

<a href="https://ibb.co/cOp6N5"><img src="https://preview.ibb.co/cbWLh5/tab.gif" alt="tab" border="0" /></a>

### How it will Work
TabManager will work almost exclusively based off of configs, but will have a command to change values in those configs. 
<br>
Upon first boot, the plugin will create a folder in the plugins folder which will contain a subfolder for each plugin (with the exception of this one). Each subfolder will then have a YAML file named after each command in the plugin. From there, you will be able to add tab lists specific to argument length and a whole lot of others things.

### Versions
Versions will be named by the <a href="https://en.wikipedia.org/wiki/Greek_alphabet">Greek Alphabet</a>, followed by the plugin name and a number to identify which user pressed the final edit. If the build is the final one of the version, it will be a capital letter, if not, it will be a lowercase letter.

<br>
<br>
For example, "α-TabManager-9"
<br>
Version: <i>Alpha</i>
<br>
Last: <i>false</i>
<br>
Plugin: <i>TabManager</i>
<br>
Number: <i>9</i> (TheEnderCrafter9)
