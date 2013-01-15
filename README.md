BoxcarConnectorModel
====================

This is a small Java project that I used for my own purposes. With my BoxcarConnectorModel you can easily
send notifications to a Boxcar (http://boxcar.io) account - which are then pushed to the Boxcar App.

In short: you can send iOS notifications.

Use
===

Create a new Boxcar provider. This is free and can be done here: http://boxcar.io/site/providers
Download and import my java class. Then use it as follow:

Create a new instance of the class:

BoxcarConnectorModel m = new BoxcarConnectorModel("your Boxcar Provider APIKey here");

Send invitations to your Boxcar Provider as follow:

m.sendInvitation("reciever e-mail");

Send notifications to already subscribed users as follow:

m.sendMessage("reciever e-mail","message","your name");

That's it.
