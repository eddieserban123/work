import React, { Component } from 'react';
import "./classroom.css"

class classRoom extends Component {
    state = {  }
    render() { 
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
                        <img class="resize" src = {`http://localhost:8080/image/room?number=${classroom.room_number}&year_month=2019-01`}/>
                    </div> 
                    <div class = "col-md-5">
                        <p class="capacity">{classroom.capacity}</p>
                        <p>sdsdsd</p>
                    </div>
                </div>    
            </div>
         );
    }
}
 
export default classRoom;