# Paging-3-ToDo-app

## Application Description

it's a sample app utilizing the power of Paging library 3 (like caching data and handling errors) exposing the requested data(Retrofit,Room) through kotlin flow and rx java 3 integration also
dagger-hilt used as dependency injection framework


## How to Run
install the app and use the bottom navigation to navigate between 4 screens each screen will represent a list of notes



## Application Structure

-app consists of 4 fragments 1- get the notes from the api using retrofit ,flow,paging source
                            2- get the notes  from the api using retrofit ,rxjava3,rxpaging source
                            3- get the notes cached in Room using retrofit ,Room,RemoteMediator,flow
                            4- get the notes cached in Room using retrofit ,Room,RemoteMediator,rxjava3
                            
  -Skills Used: dagger-hilt,navigation components,coroutines+flows,Paging 3,rxjava3,Room,Retrofit,MVVM Pattern


## App Views
 - FlowPagingSourceFragment
 -RxPagingSourceFragment
 -FlowRemoteMediatorFragment
 -RxRemoteMediatorFragment


## Improvement Points
-unit testing
