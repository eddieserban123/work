import React, { Component } from 'react';
import logo from './logo.svg';
import './main-page.css';
import Header from './header';


class App extends Component {
  state = {}

 componentDidMount() {
   this.fetchPersons();
 }

  fetchPersons =() => {

    let url = "http://localhost:8080/person";
    let username = 'user';
    let password = 'user';

    let h = new Headers();


    h.append('Accept', 'application/json');
    h.append('Authorization', 'Basic ' + btoa(username + ":" + password));
    h.append('Content-Type', 'application/x-www-form-urlencoded');
    console.log('Basic ' + btoa(username + ":" + password));
    fetch(new Request(url, {method:'GET', headers: h}))
    .then(rsp => rsp.json())
    .then(persons => {
      this.persons = persons;
    })
  }

  render() {
    return (
    <div classname="container">
    <Header subtitle="New way of keeping reports"/>
    </div>
    );
  }
}

export default App;
