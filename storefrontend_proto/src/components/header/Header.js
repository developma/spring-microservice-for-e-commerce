import React, {Component} from 'react'

export default class Header extends Component {
  constructor (props) {
    super(props)
    this.state = {
      totalUnit: 0,
      totalFee: 0
    }
  }

  onClickAddToCart (e) {
    this.setState({
      totalUnit: 99,
      totalFee: 100
    })
  }

  render () {
    return (
      <nav className="navbar navbar-inverse" role="navigation">
        <div className="container">
          <div className="navbar-header">
            <a className="navbar-brand" href="/">storefrontend</a>
          </div>
          <ul className="nav navbar-nav navbar-right">
            <li>
              <button className="btn btn-success navbar-btn">
                Checkout <span className="badge">{this.state.totalUnit}({this.state.totalFee} Yen)</span>
              </button>
            </li>
          </ul>
        </div>
      </nav>
    )
  }
}