Wolf3D
==========
This is the CRC Cards:
https://docs.google.com/document/d/1DsrmYF5gw9SnOiSaHGH8asVmqk_TrPCyu8xrBzLsfeg/edit?usp=sharing

Networking Sequence Diagram:
https://drive.google.com/file/d/0B3TM3EqE54HCYjVaQnBIMElkZW8/view?usp=sharing


This is a start for the class diagram:
https://drive.google.com/file/d/0B2CawmmmcfjSSF8tNzVQQWVieWM/view?usp=sharing

If you want to edit the file use this link (u will need draw.io to edit):
https://drive.google.com/file/d/0B2CawmmmcfjScTc3aTQ2LWo0c1k/view?usp=sharing

Note: Need system to generate unique IDs for Entities.

Key concepts:

  The X axis is left:-1 and right:1
  The Y axis is bottom:-1 and top:1
  The Z axis is far:-1 and near:1 (i.e z points out of the screen at you)
  
  The Component based design idea relies on making well formed 
    highly encapsulated components that just do their specific task.
  
  i.e. Instead of making a 'Player' class - You instantiate an Entity 
    (an empty shell that holds components) and then add all the functionality
    you need to this Entity in the form of Components. As opposed to bunging
    all that functionality haphazardly into a 'Player' spaghetti class.

Entry into the program:

wolf3d.Wolf3D contains a main method which instantiates itself:
 - Creating a JFrame (itself).
 - Registers the Input classes Mouse and Keyboard.
 - Creates a new World.
 - Adds a test entity to the world.
 - Creates the WorldView - passing in the World.
 - packs and sets itself to be visible.
 - Creates a GameDemo - passing in the World.
 - Sets the View of the GameDemo to be the WorldView we created before.
 - Starts the game running.

GameLoop is a thread that can be extended allowing you to create a class such as GameDemo easily.
