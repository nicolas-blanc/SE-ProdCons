package jus.poc.prodcons.v;

import java.util.Random;

import jus.poc.prodcons.Message;

public class MessageX implements Message {

	@Override
	public String toString() {
		return "Le message est : " + new Random().nextInt();
	}

	
}
