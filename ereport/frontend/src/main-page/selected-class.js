import React from 'react';
import ClassRoom from '../classroom';

const selectedClass = (props) => {
    console.log("selected class");  
    console.log(JSON.stringify(props));
    if (props.classRoom) 
      return ( 
          <div>
              <div class="row selectedClass">
                  <h3 class="col-md-12 text-center"> Selected class </h3>
              </div>
              <ClassRoom classRoom={props.classRoom}/>
        </div>
       )

       return (<div>No ClassRoom selected</div>)
}
 
export default selectedClass;