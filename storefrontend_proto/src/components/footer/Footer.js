import React, {Component} from 'react'
import './Footer.css'

export default class Footer extends Component {
  render () {
    return (
      <footer className="navbar-default navbar-bottom navbar-inverse">
        <div className="container-fluid">
          <p className="navbar-text footer-navbar-text-center">
            Checkout repository at 
            <a href="https://github.com/developma/spring-microservice-for-e-commerce"> github</a>
          </p>
        </div>
      </footer>
    )
  }
}
