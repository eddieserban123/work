import React from 'react';

class  KidsClassRoomRow extends Component {
   
    state = {activeClassRoom:{},
            kids:[]
        }


    componentDidMount() {
        this.fetchKids();
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



    render() { 
        return (
            <tr>
                <td>props.kid.name</td> 
            </tr> 
          );
    }
}
   
export default KidsClassRoomRow;
