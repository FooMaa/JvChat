DELETE FROM chat_schema.chats_messages;

INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('1', '1', '1', decode('013d7d16d7ad4fefb61bd95b765c8ce0', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('1', '1', '1', decode('013d7d16d7ad4fefb61bd95b765c8ce1', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('1', '2', '2', decode('013d7d16d7ad4fefb61bd95b765c8ce2', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('2', '3', '2', decode('013d7d16d7ad4fefb61bd95b765c8ce3', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('3', '1', '2', decode('013d7d16d7ad4fefb61bd95b765c8ce4', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('3', '1', '2', decode('013d7d16d7ad4fefb61bd95b765c8ce5', 'hex'));
INSERT INTO chat_schema.chats_messages (senderID, receiverID, status, message) VALUES ('1', '3', '2', decode('013d7d16d7ad4fefb61bd95b765c8ce6', 'hex'));
