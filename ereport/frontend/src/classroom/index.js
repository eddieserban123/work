import React, { Component } from 'react';
import "./classroom.css"

class classRoom extends Component {
    state = {  }
    render() { 
        const classroom = this.props.classRoom;
        return ( 
            <div>
               <div className="row mt-2">
                    <h5 className="col-md-12"> {classroom.id}</h5>
                </div> 

                <div className="row">
                    <h3 className="col-md-12"> {classroom.room_number}</h3>
                </div> 
                <div className="row">
                    <div className="col md-7">
                        <img src = {`https://picsum.photos/id/${classroom.room_number}/600/400`}/>
                    </div> 
                    <div className = "col-md-5">
                        <p className="capacity">{classroom.capacity}</p>
                        <p>sdsdsd</p>
                    </div>
                </div>    
            </div>
         );
    }
}
 
export default classRoom;