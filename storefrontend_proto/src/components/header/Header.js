import React, {Component} from 'react'
import request from 'superagent'

export default class Header extends Component {
  constructor (props) {
    super(props)
    this.state = {
      totalUnit: 0,
      totalFee: 0
    }
    this.itemList = []
  }

  componentWillReceiveProps (nextProps) {
    const item = nextProps.item
    this.itemList.push(item)
    let totalFee = 0
    this.itemList.forEach(item => {
      totalFee = totalFee + item.price
    })
    this.setState({
      totalUnit: this.itemList.length,
      totalFee: totalFee
    })
  }

  onCheckOutClick (e) {
    if (this.state.totalUnit === 0) {
      return alert('please add some items to cart.')
    }

    const orderedItem = []
    this.itemList.forEach(item => {
      orderedItem.push({
        registerId: 0,
        id: item.id,
        unit: 1,
        versionno: 0
      })
    })

    // This is dummy data for passing process of order.
    const order = {
      registerId: 0,
      orderedItem: orderedItem,
      senderName: 'TestTestTest',
      address: {
        registerId: 0,
        zipCode: '123-4567',
        location: 'TestLocTestLoc',
        receiverName: 'NameNameName'
      }
    }

    request.post('http://localhost:8210/shipping/order/')
    .set('Content-Type', 'application/json')
    .send(order)
    .end((err, res) => {
      if (err || !res.ok) {
        const errorInfo = JSON.parse(res.text)
        if (errorInfo) {
          window.alert(errorInfo.message)
        } else {
          window.alert('post error' + JSON.stringify(err))
          console.log(JSON.stringify(err))
        }
      } else if (res.text === 'success') {
        window.alert('your order has been completed.')
        console.log(JSON.stringify(res))
        this.itemList.splice(0, this.itemList.length)
        this.setState({
          totalUnit: 0,
          totalFee: 0
        })
      } else {
        window.alert('an error occurs during ordering process.')
      }
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
              <button className="btn btn-success navbar-btn" onClick={e => this.onCheckOutClick(e)}>
                Checkout <span className="badge">{this.state.totalUnit}({this.state.totalFee} Yen)</span>
              </button>
            </li>
          </ul>
        </div>
      </nav>
    )
  }
}
