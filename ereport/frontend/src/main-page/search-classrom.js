import React, { Component } from 'react';


class SearchClassRoom extends Component {
    state = {
        selectedOption: null,
      }

    onSearchChange=(e) => {
        const classroom=e.target.value;
        this.setState({selectedOption: classroom });
        this.props.setActiveClassRoom(classroom);
    }
    render() { 
        const search = this.state.selectedOption;
        const classrooms = this.props.classRooms || [];
        return (  
            <div class="form-group row mt-3">
                <div class="offset-md-2 col-md-4">
                Search for a class    
                </div>
                <div class="col-md-4">
                    <select class="form-control" value={search} onChange={this.onSearchChange}>
                    {classrooms.map((c,i) => <option key={c.id} value={i}>{c.id}</option>)}
                    </select>    
                </div>
                
            </div>
        );
    }
}
 
export default SearchClassRoom;