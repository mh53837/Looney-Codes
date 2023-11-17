import React from 'react';
import { Link } from 'react-router-dom';
class NotFoundPage extends React.Component{
    render(){
        return <div>
            <p style={{textAlign:"center"}}>
              <p><b>404 page not found</b></p>
              <Link to="/">Go to Home </Link>
            </p>
          </div>;
    }
}export default NotFoundPage;
