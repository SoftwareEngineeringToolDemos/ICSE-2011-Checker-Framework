## Step by step guide to set up the Virtual Machine using Vagrant.

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

### References:
* https://docs.vagrantup.com/v2/getting-started/up.html

