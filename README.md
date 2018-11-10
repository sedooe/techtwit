# TechTwit
A [telegram bot](https://telegram.me/techtwit_bot) which sends subscribers technical tweets/articles about software development, JVM, Java, Javascript, Kubernetes and so on.

## Commands
`/start` -> Makes you a subscriber.

`/new` -> Sends you a new article.

`/read` -> Only usable on replies. By replying with this command to a previously sent article, you indicate that you read the article and the bot will not send you that article again.

## Possible features
1) Send an article everyday at 10 PM to all users.
2) Make the time above configurable by users.
3) Allow users to blacklist an article. Functionally, this is the same with `/read` command at this moment.
4) Allow users to mark an article as `irrelevant`. This would require an approvement from admin(s).
5) Allow users to `suggest` articles. This would require an approvement from admin(s).
