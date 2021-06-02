import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './metric-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMetricDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MetricDataDetail = (props: IMetricDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { metricDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metricDataDetailsHeading">MetricData</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{metricDataEntity.id}</dd>
          <dt>
            <span id="timeStamp">Time Stamp</span>
          </dt>
          <dd>
            {metricDataEntity.timeStamp ? <TextFormat value={metricDataEntity.timeStamp} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="measureValue">Measure Value</span>
          </dt>
          <dd>{metricDataEntity.measureValue}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{metricDataEntity.name}</dd>
          <dt>Status</dt>
          <dd>{metricDataEntity.status ? metricDataEntity.status.id : ''}</dd>
          <dt>Metric</dt>
          <dd>{metricDataEntity.metric ? metricDataEntity.metric.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/metric-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/metric-data/${metricDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ metricData }: IRootState) => ({
  metricDataEntity: metricData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricDataDetail);
