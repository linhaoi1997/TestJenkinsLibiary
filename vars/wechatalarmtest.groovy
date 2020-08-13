@Grab('io.github.http-builder-ng:http-builder-ng-okhttp:0.14.2') 
import static groovy.json.JsonOutput.toJson 
import static groovyx.net.http.HttpBuilder.configure 

def posts = configure { 
    request.uri = 'https://jsonplaceholder.typicode.com' 
    request.uri.path = '/posts' 
    request.contentType = 'application/json' 
    request.body = toJson(title: 'food', body: 'bar', userId: 1) 
}.post() 

assert posts.title == 'foo' 