# Artlab Client

Artlab Client(artlab-client) provides a full featured and easy to consume java library for working with Artlab via the Artlab REST API

[Using Artlab API Client](#using-artlab-api-client)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[Project Set Up](#project-set-up)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[Usage Example](#usage-example)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[Making API Calls](#making-api-calls)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Repository API](#repository-api)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Generic Repo API](#generic-repo-api)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Module & Version API](#module-version-api)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Rsync API](#rsync-api)<br/>

## Using Artlab API Client

### Project Set Up

To utilize artlab api client in your java project, simple add the following dependency to your project's build file:

#### Gradle: build.gradle

```groovy
dependencies {
    ...
    compile group: 'com.alibaba.aone', name: 'artlab-client', version: '1.0.0'
}
```

#### Maven: pom.xml

```xml

<dependency>
    <groupId>com.alibaba.aone></groupId>
    <artifactId>artlab-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Usage Example

Artlab Api Client is quite simeple to sue, all you need is the URL to you Artlab server and the Personal access credential from your Artlab account region setting page. Once you have that info it is as simple as:

```java
// Create the ArtlabApi instance to communicate with your Artlab server
ArtlabApi artlabApi=new ArtlabApi.Builder()
        .withServer("http://localhost:80/")         // artlab server address
        .withBasicCredential("nWdSjO","dRdag2rq7O") // basic auth username and password
        .withOrg("5f8d09c030352652858490bb")        // artlab orgnazation id
        .build();

// Get the list of repository with type 'GENERIC' with your orgnazation
List<Repository> repos=artlabApi.getRepositoryApi().listRepos(RepoType.GENERIC);
```

### Setting Request Timeouts

You can add the setting for connect and read timeouts for the API Client

```java
ArtlabApi artlabApi=new ArtlabApi.Builder()
        .withServer("http://localhost:80/")
        .withBasicCredential("nWdSjO","dRdag2rq7O")
        .withOrg("5f8d09c030352652858490bb")
        .withClientConfiguration(ArtlabApiClientConfiguration.DEFAULT
        	.withConnectionTimeoutInSecond(10) // connect timeout 10s
        	.withReadTimeoutInSecond(30)       // read timeout 30s
        ).build();
```

## Making API Calls

The following is a list of the available sub APIs along with a sample use of each API. 

### Repository API

Repository API is used to access the resources of the repository, such as listing repository, creating repository, updating repository strategies, etc.

```java
RepositoryApi repoApi = artlabApi.getRepositoryApi();
```

#### List repositories 

List repositories in the organization.

list the repository by list request param
```java
// List all Docker Repositories in the organization
List<Repository> repositories = artlabApi.getRepositoryApi()
        .listRepos(new ListReposRequest()
            .withRepoType(RepoType.GENERIC)
            .withPage(0, 20)
        );
```
list the repository by repo type param
```java
List<Repository> repos = artlabApi.getRepositoryApi().listRepos(RepoType.GENERIC);
```

### Generic Repo API

The Generic Repo API is used to operate the resources of the generic repository, such as uploading files, downloading files, etc. 

Note that this API only applies to GENERIC repositories

```java
GenericApi genericApi=artlabApi.getGenericApi()
```

#### Upload file
Make a upload generic file request, upload the file to generic repo.
```java
UploadGenericFileRequest uploadGenericFileRequest=new UploadGenericFileRequest()
        .withRepoId("1-generic-eft21s")      // upload target generic repository id
        .withVersion("v1.2.3")               // upload file version
        .withUploadPath("hello/qeesung")     // upload file path
        .withUploadFile(new File("upload")); // file to be upload
GenericFile genericFile=artlabApi.getGenericApi().upload(uploadGenericFileRequest);
```

#### Download file
Make a download generic file request from generic repo, the remote file input stream will return
```java
DownloadGenericFileRequest request = new DownloadGenericFileRequest()
        .withRepoId("1-generic-eft20s")     // download target generic repository id
        .withVersion("v1.2.3")              // download file version
        .withFilepath("aaa/bbb/ccc/1.jar"); // download file path
InputStream download = artlabApi.getGenericApi().download(request);
FileUtils.copyInputStreamToFile(download, new File("/tmp/output.jar"));
```

Download the latest version of the file
```java
DownloadGenericFileRequest request = new DownloadGenericFileRequest()
        .withRepoId("1-generic-eft20s")     // download target generic repository id
        .withVersion("latest")              // latest version
        .withFilepath("aaa/bbb/ccc/1.jar"); // download file path
InputStream latestStream = artlabApi.getGenericApi().download(request);
```



### Module & Version API

Module & Version API is used to list the modules under a repository, or to delete a module, a version

```java
RepositoryApi repositoryApi = artlabApi.getRepositoryApi()
```

#### List modules 

List the modules in the repository

```java
List<Module> modules = artlabApi.getModuleApi().listModules(new ListModulesRequest()
                .withRepoId("1-generic-eft21s")   // repository id
                .withRepoType(RepoType.GENERIC)); // repository type
```

#### List module versions

List the versions in the module

```java
List<Version> versions = artlabApi.getModuleApi().listVersions(
        new ListVersionsRequest()
                .withRepoId("1-generic-eft21s")                // repository id
                .withRepoType(RepoType.GENERIC)                // repository type
                .withModule(module.getOrg(), module.getName()) // module org and name
 );
```



### Rsync API

Rsync API is used to synchronize the artifact to the follow site.

```java
RsyncApi rsyncApi = artlabApi.getRsyncApi()
```

#### List Rsync Tasks

List the rsync tasks in the repository.

```java
List<RsyncTask> rsyncTasks = artlabApi.getRsyncApi().listRsyncTasks(
        new ListRsyncTasksRequest()
                .withRepoId("1-generic-eft21s")  // repository id
                .withRepoType(RepoType.GENERIC)  // repository type
                .withPage(0, 20)                 // page num and page size
);
```

#### Add Rsync Task

Add a new rsync task for a module version in the repository.

```java
// add rsync task by site
RsyncTask rsyncTask = artlabApi.getRsyncApi().addRsyncTask(
        new AddRsyncTasksRequest()
                .withRepoId("1-generic-eft21s") // repository id
                .withRepoType(RepoType.GENERIC) // repository type
                .withModule("org1", "module1")  // module org and name
                .withVersion("version1")        // version number
                .withRsyncSite("follow-site")   // rsync site name
);

// add rsync task by type
RsyncTask task = artlabApi.getRsyncApi().addRsyncTask(
        new AddRsyncTasksRequest()
                .withRepoId("1-generic-eft21s")
                .withRepoType(RepoType.GENERIC)
                .withModule("org1", "module1")
                .withVersion("version1")
                .withRsyncType("production")   // rsync type
);
```



