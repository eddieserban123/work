import React, { Component } from 'react';
import "./classroom.css"

class classRoom extends Component {
    state = {  }
    render() { 
        console.log( JSON.stringify(this.props.classRoom));
        const classroom = this.props.classRoom;
        return ( 
            <div>
               <div class="row mt-2">
                    <h5 class="col-md-12"> {classroom.id}</h5>
                </div> 

                <div class="row">
                    <h3 class="col-md-12"> {classroom.room_number}</h3>
                </div> 
                <div class="row">
                    <div class="col-md-7">
                        <img class="resize" src = {`http://localhost:8080/image/room?number=${classroom.roomNumber}&year_month=${classroom.year_month}`}/>
                    </div> 
                    <div class = "col-md-5">
                        <p class="capacity">capacity of max {classroom.capacity} children allowed </p>
                        <p>{classroom.description}</p>
                    </div>
                </div>    
            </div>
         );
    }
}
 
export default classRoom;