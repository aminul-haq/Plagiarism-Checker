# Plagiarism-Checker


This project can detect plagirsim by measuring similarities between two input input text, which works for both Bangla and English languages. It can detect plagirism even if sentences are modified with transformation or synonyms.

We used saveral steps to find similarities.

	1. First we converted all the words to its base form. (See the examples below)
	2. Then we tried to find the best matched line from input.

Notes:

	1. It's an approximation approach. Results are not hundred percent accurate. 
	2. Proving more data (dictionary, synonyms etc) will result in better output. 

Example(English):

	1. He eats mango.
	2. Mangoes are eaten by him.

	First line's words  will be converted to:
	He = he; eats = eat; mango = mango.
	Second line's words  will be converted to:
	Mangoes = mango; are = be; eaten = eat; by = by; him = he
	Total matching  = 3 out of 5 words (mango, eat, he) (60% plagiarism)


Example(Bangla):

	1.ডাক্তার আসার আগেই রোগী মারা গেল
	2. রোগীর মৃত্যুর পর ডাক্তার আসল

	First line's words  will be converted to:
	ডাক্তার = ডাক্তার; আসার = আসা; আগেই = আগে; মারা = মৃত্যু; গেল = গেল;
	Second line's words  will be converted to:
	রোগীর = রোগী; মৃত্যুর = মৃত্যু; পর = পর; ডাক্তার = ডাক্তার; আসল = আসা;
	Total matching  = 4 out of 6 words (রোগী, মৃত্যু, ডাক্তার, আসা) (80% plagiarism)


How To Use:

	1. First clone the whole project. 
	2. Make sure all the text files and the Java file are in the same folder.
	3. Copy your orignal text to file_1.txt and another text to file_2.txt.
	4. Run the Java program.
	5. Your output will be saved in output.txt file.


