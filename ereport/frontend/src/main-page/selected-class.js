import React from 'react';
import ClassRoom from '../classroom';

const selectedClass = (props) => {
    if (props.class) 
      return ( 
          <div>
              <div className="row selectedClass">
                  <h3 className="col-md-12 text-center"> Selected class </h3>
              </div>
              <ClassRoom class={props.class}/>
        </div>
       )

       return (<div>No ClassRoom selected</div>)
}
 
export default selectedClass;