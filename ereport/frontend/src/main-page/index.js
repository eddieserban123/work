import React, { Component } from 'react';
import logo from './logo.svg';
import './main-page.css';
import Header from './header';
import SelectedClass from './selected-class';


class App extends Component {
  state = {classRooms:[{"id":"small 1", "capacity":12, "room_number":23}]}

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
      this.classRooms = classRooms;
      this.state= {classRooms: classRooms}; 
    })
  }

  render() {
    return (
    <div classname="container">
    <Header subtitle="New way of keeping reports"/>
    <SelectedClass classRoom = {this.state.classRooms[0]} />
    </div>
    );
  }
}

export default App;
