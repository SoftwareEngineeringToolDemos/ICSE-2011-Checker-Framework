# Contents of build-vm folder
In this folder you will find:
*  **Vagrantfile** - Contains the vagrant script to load the Ubuntu 15 Desktop VM. 
***

# Minimum requirements for using this Vagrant Script 
* Any 64-bit OS
* VirtualBox (preferrably 5.0.6 or later)
* Vagrant (preferrably 1.7.4) 

***

# Step by step guide to set up the Virtual Machine using Vagrant.

### Here are the steps:
* Install [Vagrant](https://www.vagrantup.com/downloads.html) and [VirtualBox](https://www.virtualbox.org/wiki/Downloads).
* Create a new directory and place this [VagrantFile](https://github.com/SoftwareEngineeringToolDemos/ICSE-2011-Checker-Framework/blob/master/build-vm/Vagrantfile) in it.
* Work your way to this directory from the terminal using the cd command.
* Use the command "vagrant up". This will create an Ubuntu 14.04 LTS VM with openjdk-1.7 installed in it.
* You can log into the VM, using either of the two approaches:
  * VirtualBox's GUI
  * An ssh client(For example Putty) to start a new ssh session
* Credentials required for login.
  * Username: vagrant
  * Password: vagrant
  
# Installed software
  * Java OpenJDK version 8. 

### References:
* https://docs.vagrantup.com/v2/getting-started/up.html
 
### This vagrant file makes use of the following basebox 
* https://atlas.hashicorp.com/rudolfochrist/boxes/ubuntu-desktop
This is a standard and stable external resource. This is identical to the operating system as used by the hand built VM.
* A simple tutorial to help new users install Java onto their machines can be viewed [here] (https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get)
* A tutorial to create the Vagrant script was followed which can be found [here] (https://blog.engineyard.com/2014/building-a-vagrant-box)

