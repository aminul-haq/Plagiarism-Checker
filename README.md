# Plagiarism-Checker

##CSE299(Junior Design) project##
##Fall 2018##

There are two different types to automatic plagiarism checking:

1. One is based on checking the similarity of the source document with the documents present in the reference text dataset. It is called ordered matching.

2. Another is based on checking the base words. Its our own method to find plagiarised copy. It is unordered matching.

Unordered Matching:

		Match the frequency of each words in a line.
		Substring matching.
		Complexity: o(n) 

Ordered Matching:

		Using longest common subsequences(LCS).
		Complexity: o(n*m)

Example(English):

		1. He eats mango.
		2. Mangoes are eaten by him.
Line one will be converted to:
		He = he; eats = eat; mango = mango.
Line two will be converted to:
		Mangoes = mango; are = be; eaten = eat; by = by; him = he.

Unordered matching  = 3 out of 5 words (mango, eat, he)
Ordered matching = 1 of 5 words (mango / eat / he – any one of these three)


Example(Bangla):

		ডাক্তার আসার আগেই রোগী মারা গেল
		রোগীর মৃত্যুর পর ডাক্তার আসল
Line one will be converted to:
		ডাক্তার = ডাক্তার; আসার = আসা; আগেই = আগে; মারা = মৃত্যু; গেল = গেল;
Line two will be converted to:
		রোগীর = রোগী; মৃত্যুর = মৃত্যু; পর = পর; ডাক্তার = ডাক্তার; আসল = আসা;

Unordered matching  = 4 out of 6 words (রোগী, মৃত্যু, ডাক্তার, আসা)
Ordered matching = 2 of 6 words (রোগী, মৃত্যু)


How To Use:

First clone the whole project. 
Before open the .java file just make sure all files are in same folder and then run the .java file from your IDE.
Save your main input in file_1.txt and the other one in file_2.txt
Your output'll show up output.txt


