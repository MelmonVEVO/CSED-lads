import React from 'react';

import { VictoryChart, VictoryLine, VictoryAxis } from 'victory';

export default class MoodGraph extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <VictoryChart>
                <VictoryAxis style={{ tickLabels: { fontSize: 6 } }} />
                <VictoryAxis dependentAxis tickValues={[1,2,3,4,5]} height={300}/>
                <VictoryLine data={this.props.data} />
            </VictoryChart>
        );
    }
}