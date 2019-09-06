import React, { Component } from 'react';
import logo from './logo.svg';
import './main-page.css';
import Header from './header';
import SelectedClass from './selected-class';
import SearchClassRoom from './search-classrom'


class App extends Component {
  state = {activeClassRoom:{},
           classRooms:[]}
  
 
 componentDidMount() {
  this.fetchClassRooms();
}

  fetchClassRooms =() => {

    let url = "http://localhost:8080/classroom";
    let username = 'user';
    let password = 'user';

    let h = new Headers();


    h.append('Accept', 'application/json');
    h.append('Authorization', 'Basic ' + btoa(username + ":" + password));
    h.append('Content-Type', 'application/x-www-form-urlencoded');
    console.log('Basic ' + btoa(username + ":" + password));
    fetch(new Request(url, {method:'GET', headers: h}))
    .then(rsp => rsp.json())
    .then(classRooms => {
      console.log(JSON.stringify(classRooms));    
//      this.classRooms = classRooms;
     this.setState({activeClassRoom:classRooms[0]});
     this.setState({classRooms}); 

    })
  }

  setActiveClassRoom = (classRoomIndex) => {
    console.log(JSON.stringify(classRoomIndex));
    this.setState({activeClassRoom:this.state.classRooms[classRoomIndex]});
  }

  render() {
    return (
    <div class="container">
    <Header subtitle="New way of keeping reports"/>
     <SearchClassRoom classRooms= {this.state.classRooms} setActiveClassRoom={this.setActiveClassRoom}/>
     <SelectedClass classRoom = {this.state.activeClassRoom} /> 
    </div>
    );
  }
}

export default App;
