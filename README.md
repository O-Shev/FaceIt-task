*this application is task for applying for a job in [Face IT](https://jobs.dou.ua/companies/faceit/). 
Task Requirements [here](https://docs.google.com/document/d/1Ukb4yhGjhZs6eKO4x1cR5LMaSlor0k47d6rvqsAvq1U/edit)*

Currently, this project is deployed on my personal laptop through Cloudflare tunnel. 
Base url is
`https://faceit-task.geotalker.com/`  
Then for example you can use it to get pageable list of jobs by  
`https://faceit-task.geotalker.com/job/all?page=2&size=75`

*If the server is not reachable, but you want to test it, so don't be shy to write me at `oleksii.shevchenko.2000@gmail.com` and I will quickly make it reachable.*


<!-- TOC -->
  * [API Endpoints](#api-endpoints)
    * [Get All Jobs](#get-all-jobs)
      * [Overview](#overview)
      * [HTTP Method](#http-method)
      * [Request Parameters](#request-parameters)
      * [Response Example](#response-example)
    * [Get Top Jobs](#get-top-jobs)
      * [Overview](#overview-1)
      * [HTTP Method](#http-method-1)
      * [Response Example](#response-example-1)
    * [Get location stats](#get-location-stats)
      * [Overview](#overview-2)
      * [HTTP Method](#http-method-2)
      * [Response Example](#response-example-2)
  * [Maintaining Database](#maintaining-database)
  * [Deployment](#deployment)
<!-- TOC -->


## API Endpoints
### Get All Jobs
#### Overview
Retrieve a paginated list of all jobs ordered by date (from newest to oldest).
#### HTTP Method
`GET /job/all`

#### Request Parameters
- `page` (optional): The page number to retrieve. Default is `0`.
    - Type: `int`
    - Example: `0`
- `size` (optional): The number of jobs to retrieve per page. Default is `50`, maximum is `100`.
    - Type: `int`
    - Example: `50`

#### Response Example
```json
[
  {
    "slug": "kyc-analyst-frankfurt-314019",
    "company_name": "univativ GmbH",
    "title": "KYC Analyst (m/w/d)",
    "description": "<p>...</p>",
    "remote": false,
    "url": "https://www.arbeitnow.com/jobs/companies/univativ-gmbh/kyc-analyst-frankfurt-314019",
    "tags": [
      "Finance"
    ],
    "job_types": [],
    "location": "Frankfurt",
    "created_at": 1721634543
  }
]
```

### Get Top Jobs
#### Overview
Retrieve top 10 the most popular jobs
> API of [arbeitnow](https://www.arbeitnow.com/) (data resource for task) 
> not provide any info that would give info about popularity. So for now it returns
> just 10 jobs ordered by date

#### HTTP Method
`GET /job/top`
#### Response Example
*see [Get All Jobs]() endpoint*

### Get location stats
#### Overview
Return number of jobs per location

#### HTTP Method
`GET /location/stats`

#### Response Example
```Json
[
    {
        "location": "Aachen",
        "jobs_number": 12
    },
    {
        "location": "Amberg",
        "jobs_number": 10
    },
    {
        "location": "Arnstadt",
        "jobs_number": 7
    }
]
```

## Maintaining Database
H2 in memory database is used. In [Arbeitnow API](https://www.arbeitnow.com/api/job-board-api) tell:
> "info": "Jobs are updated every hour and order by the `created_at` timestamp. 
> Use `?page=` to paginate. Read more information here: https://www.arbeitnow.com/blog/job-board-api"

So local H2 database also updates every hour. 

## Deployment
- **Dockerfile** provides fast containerization deployment of this application.
- **docker-compose.yml** up server by mentioned Dockerfile, and also provide service that [cloudflared](https://hub.docker.com/r/cloudflare/cloudflared) 
service for simply getting access to application by public domain thought [Cloudflare Tunnel](https://www.cloudflare.com/products/tunnel/)