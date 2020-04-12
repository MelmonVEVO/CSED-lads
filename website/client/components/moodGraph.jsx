import React from 'react';

import { VictoryChart, VictoryLine, VictoryAxis } from 'victory';

export default class MoodGraph extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <VictoryChart minDomain={{ y: 1 }} maxDomain={{ y: 5 }} >
                <VictoryAxis style={{ tickLabels: { fontSize: 6 } }} />
                <VictoryAxis dependentAxis />
                <VictoryLine data={this.props.data} />
            </VictoryChart>
        );
    }
}