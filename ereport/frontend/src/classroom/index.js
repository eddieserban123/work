import React, { Component } from 'react';
import "./classroom.css"

class classRoom extends Component {
    state = {  }
    render() { 
        const classroom = this.props.classroom;
        return ( 
            <div>
               <div className="row mt-2">
                    <h5 className="col-md-12"> {classroom.name}</h5>
                </div> 

                <div className="row">
                    <h3 className="col-md-12"> {classroom.teacher}</h3>
                </div> 
                <div className="row">
                    <div className="row md-7">
                        <img src = {'https://picsum.photos/id/${classroom.id}/600/300'}/>
                    </div> 
                    <div className = "col-md-5">
                        <p className="size">${classroom.size}</p>
                        <p>${classroom.description}</p>
                    </div>
                </div>    
            </div>
         );
    }
}
 
export default classRoom;