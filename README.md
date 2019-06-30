# single-process-communication
Two players communicate with each other in the same Java process.
First player sends a message the other player then other player replies with a message that contains the received message concatenated with the value of a counter holding the number of messages this player already sent.
If the player sent 10 messages and received back 10 messages program terminates.

Application : single-process-communication
Class Files :
1. com.karabiyikoglu.ismail.singleprocess.app.SingleProcessAppMain : Application’s main class. Process start from this class.

2. com.karabiyikoglu.ismail.singleprocess.app.MessageHandler : This class holds buffer message. We use it for sending and receiving message.

3. com.karabiyikoglu.ismail.singleprocess.app.Player : This class for creating players. It can communicate with other players via MessageHandler.

4. com.karabiyikoglu.ismail.singleprocess.app.constants.IConstants : Constant interface to hold constant variables

To run application, in shell terminal go to target directory and type "sh start.sh". You can see the sent and received messages in console output