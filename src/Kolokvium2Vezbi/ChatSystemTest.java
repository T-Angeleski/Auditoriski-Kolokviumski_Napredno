package Kolokvium2Vezbi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception {
	public NoSuchRoomException() {
		super("default");
	}

	public NoSuchRoomException(String room) {
		super(room);
	}
}

class NoSuchUserException extends Exception {
	public NoSuchUserException() {
		super("default");
	}

	public NoSuchUserException(String user) {
		super(user);
	}
}

class ChatRoom {
	String roomName;
	TreeSet<String> usernames;

	public ChatRoom(String roomName) {
		this.roomName = roomName;
		this.usernames = new TreeSet<>();
	}

	public void addUser(String username) {
		usernames.add(username);
	}

	public void removeUser(String username) {
		usernames.remove(username);
	}

	public boolean hasUser(String username) {
		return usernames.contains(username);
	}

	public int numUsers() {
		return usernames.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(roomName + "\n");
		if (usernames.size() == 0) {
			sb.append("EMPTY\n");
			return sb.toString();
		}

		usernames.forEach(i -> sb.append(i).append("\n"));
		return sb.toString();
	}
}

class ChatSystem {
	TreeMap<String, ChatRoom> roomsByName;
	TreeSet<String> users;

	public ChatSystem() {
		roomsByName = new TreeMap<>();
		users = new TreeSet<>();
	}

	public void addRoom(String roomName) {
		roomsByName.put(roomName, new ChatRoom(roomName));
	}

	public void removeRoom(String name) {
		roomsByName.remove(name);
	}

	public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
		if (!roomsByName.containsKey(roomName))
			throw new NoSuchRoomException(roomName);
		return roomsByName.get(roomName);
	}

	public String getUser(String user) throws NoSuchUserException {
		if (!users.contains(user))
			throw new NoSuchUserException(user);
		return user;
	}

	public void register(String userName) {
		users.add(userName);

		LinkedList<ChatRoom> minRooms = new LinkedList<>();
		int min = Integer.MAX_VALUE;

		for (ChatRoom cr : roomsByName.values()) {

			if (cr.numUsers() < min) {
				minRooms = new LinkedList<>();
				min = cr.numUsers();
			}

			if (cr.numUsers() == min) minRooms.add(cr);
		}

		if (minRooms.isEmpty()) return;

		minRooms.getFirst().addUser(userName);
	}

	public void joinRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
		//Exceptions handled in get methods
		getRoom(roomName).addUser(getUser(username));
	}

	public void registerAndJoin(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
		users.add(username);
		joinRoom(username, roomName);
	}

	public void leaveRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException {
		getRoom(roomName).removeUser(getUser(username));
	}

	public void followFriend(String username, String friendUsername) throws NoSuchUserException, NoSuchRoomException {
		for (Map.Entry<String, ChatRoom> room : roomsByName.entrySet()) {
			if (room.getValue().hasUser(getUser(friendUsername))) {
				joinRoom(getUser(username), room.getKey());
			}
		}
	}
}


public class ChatSystemTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if (k == 0) {
			ChatRoom cr = new ChatRoom(jin.next());
			int n = jin.nextInt();
			for (int i = 0; i < n; ++i) {
				k = jin.nextInt();
				if (k == 0) cr.addUser(jin.next());
				if (k == 1) cr.removeUser(jin.next());
				if (k == 2) System.out.println(cr.hasUser(jin.next()));
			}
			System.out.println("");
			System.out.println(cr.toString());
			n = jin.nextInt();
			if (n == 0) return;
			ChatRoom cr2 = new ChatRoom(jin.next());
			for (int i = 0; i < n; ++i) {
				k = jin.nextInt();
				if (k == 0) cr2.addUser(jin.next());
				if (k == 1) cr2.removeUser(jin.next());
				if (k == 2) cr2.hasUser(jin.next());
			}
			System.out.println(cr2.toString());
		}
		if (k == 1) {
			ChatSystem cs = new ChatSystem();
			Method mts[] = cs.getClass().getMethods();
			while (true) {
				String cmd = jin.next();
				if (cmd.equals("stop")) break;
				if (cmd.equals("print")) {
					System.out.println(cs.getRoom(jin.next()) + "\n");
					continue;
				}
				for (Method m : mts) {
					if (m.getName().equals(cmd)) {
						String params[] = new String[m.getParameterTypes().length];
						for (int i = 0; i < params.length; ++i) params[i] = jin.next();
						m.invoke(cs, params);
					}
				}
			}
		}
	}

}
