import React, {Component} from 'react';
import KidsClassRoomRow from './kids-classroom-table';

const SearchResults = (props) => {
  const kidsRows = props.kids.map(k =>
    <KidsClassRoomRow key={k.id.toString()} kid={k}/>)


  return ( 
    <div className="mt-2">
      <h4>Results for {props.classRoom} </h4>
      <table className="table table-hover">
      <tbody>
        {kidsRows}
      </tbody>
      </table> 
    </div> 
  );
}
 
export default SearchResults;
