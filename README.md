This is a simple project aimed to provide management between user and role with authentication.

# Structure
Project consists of 4 layers from up to down.

**API**

API layer defines clean API.

**Manager**

Manager layer implements complicate business logic.

`UserManager` provides user create and delete.
`RoleManager` provides role create and delete.
`UserRoleRelManager` maintains relationship between user and role.
`LoginManager` maintains login status with `token`. Here `token expire time` is set to _5 seconds_ to prohibit long-time tests.

**Service**

Service layer focuses on single module, like user or role.

**Storage**

Storage layer provieds in-memory KV storage.

# Key Design Points

**HttpApiServer**

We use java built-in `httpserver`. An `ApiRouter` is implemented to distribute http requests.

Annotation `RouterUrl` is added on method if it handles specific request. 
Once startup, each manager will register to `ApiRouter` which will scan methods with `RouterUrl`, and load them in map.
So that when a request comes, the `ApiRouter` can easily find the right method to handle.

**Exception Handle**

We defines `BusinessException`. When encountered a business error, a `BusinessException` is thrown out. 
Outer intercepter will catch it.
The error message inside will be extracted and returned in a normarlized `Response`.

**Token**

We do not simply use random, which may be corupted forcely.
We use _md5_(_user_info_ + _timestamp_ + _random-salt_) and mix three results to provide safety.

NOTICE. `token expire time` is set to _5 seconds_ to prohibit long-time tests.

**Service & Storage Manager**

To manage global service & storage. We provide global manager, which manages all services and storages.
Service can be reached via class name, and storage via constant names.

# External Lib

`Jackson`: provide json serialize and deserialize.

`commons.io`: provide convert between `String` and `Stream`.

`junit`: provide tests.

# Quick Start

Just run `Start.java`.

You may use `curl --location --request POST 'http://127.0.0.1:8000/api/createUser' --header 'Content-Type: application/json' --data-raw '{"userName":"userA","passWd":"123456"}'` for test.

# Furthur Improvement

Add log.

More security in passwd store and token generation.

Provide SSO. 