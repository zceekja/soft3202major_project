Input API: coinmarketcap

Output API: sendgrid

Claimed Tier: High Distinction

Credit Optional Feature 1: About

Credit Optional Feature 2: Splash page

Distinction Optional Feature: Spinning progress indicator

High Distinction Optional Feature: Reddit posting feature

Milestone 1 Submission:
    SHA: d0d8ed1e51f27e5662bc205bd3760a23c6458695
    URI: https://github.sydney.edu.au/kjag8350/SCD2_2022/commit/d0d8ed1e51f27e5662bc205bd3760a23c6458695

Milestone 1 Re-Submission:
    SHA: 
    URI: 

Milestone 2 Submission:
    SHA: e09dcc11ff01f09ff6e73238b40c7bc75b5f1581
    URI: https://github.sydney.edu.au/kjag8350/SCD2_2022/commit/e09dcc11ff01f09ff6e73238b40c7bc75b5f1581

Milestone 2 Re-Submission:
    SHA: e2e4dd86a8078df463e2d30d5b19be62bf745667
    URI: https://github.sydney.edu.au/kjag8350/SCD2_2022/commit/e2e4dd86a8078df463e2d30d5b19be62bf745667

Exam Base Commit:
    SHA: e2e4dd86a8078df463e2d30d5b19be62bf745667
    URI: https://github.sydney.edu.au/kjag8350/SCD2_2022/commit/e2e4dd86a8078df463e2d30d5b19be62bf745667

Exam Submission Commit:
    SHA: 1df6289040aeebaaf78f5f3a54ee06324381183c
    URI: https://github.sydney.edu.au/kjag8350/SCD2_2022/commit/1df6289040aeebaaf78f5f3a54ee06324381183c

Any quirks to running your application the marker needs to know about

    - Every api call, I set thread.sleep for 3 seconds to mock slow network to show:
        1. progress indicator bar
        2. responsive GUI

    - Please set following enviromental viriables:
        1. INPUT_API_KEY : for coinmarketcap
        2. SENDGRID_API_KEY: for sendgrid
        3. SENDGRID_API_EMAIL: for sendgrid email
        4. REDDIT_CLIENT_ID: for reddit script client id
        5. REDDIT_CLIENT_SECRET: for reddit script client secret

        Note: If you dont have REDDIT_CLIENT_ID and REDDIT_CLIENT_SECRET, please follow "First Steps" in this guide https://github.com/reddit-archive/reddit/wiki/OAuth2-Quick-Start-Example

    - When login to reddit please use account that create REDDIT_CLIENT_ID and REDDIT_CLIENT_SECRET
