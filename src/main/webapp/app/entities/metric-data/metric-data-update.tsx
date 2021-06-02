import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStatus } from 'app/shared/model/status.model';
import { getEntities as getStatuses } from 'app/entities/status/status.reducer';
import { IMetric } from 'app/shared/model/metric.model';
import { getEntities as getMetrics } from 'app/entities/metric/metric.reducer';
import { getEntity, updateEntity, createEntity, reset } from './metric-data.reducer';
import { IMetricData } from 'app/shared/model/metric-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMetricDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MetricDataUpdate = (props: IMetricDataUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { metricDataEntity, statuses, metrics, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/metric-data');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStatuses();
    props.getMetrics();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.timeStamp = convertDateTimeToServer(values.timeStamp);

    if (errors.length === 0) {
      const entity = {
        ...metricDataEntity,
        ...values,
        status: statuses.find(it => it.id.toString() === values.statusId.toString()),
        metric: metrics.find(it => it.id.toString() === values.metricId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="smartfactoryApp.metricData.home.createOrEditLabel" data-cy="MetricDataCreateUpdateHeading">
            Create or edit a MetricData
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : metricDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="metric-data-id">ID</Label>
                  <AvInput id="metric-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="timeStampLabel" for="metric-data-timeStamp">
                  Time Stamp
                </Label>
                <AvInput
                  id="metric-data-timeStamp"
                  data-cy="timeStamp"
                  type="datetime-local"
                  className="form-control"
                  name="timeStamp"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.metricDataEntity.timeStamp)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="measureValueLabel" for="metric-data-measureValue">
                  Measure Value
                </Label>
                <AvField id="metric-data-measureValue" data-cy="measureValue" type="text" name="measureValue" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="metric-data-name">
                  Name
                </Label>
                <AvField id="metric-data-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="metric-data-status">Status</Label>
                <AvInput id="metric-data-status" data-cy="status" type="select" className="form-control" name="statusId">
                  <option value="" key="0" />
                  {statuses
                    ? statuses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="metric-data-metric">Metric</Label>
                <AvInput id="metric-data-metric" data-cy="metric" type="select" className="form-control" name="metricId">
                  <option value="" key="0" />
                  {metrics
                    ? metrics.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/metric-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  statuses: storeState.status.entities,
  metrics: storeState.metric.entities,
  metricDataEntity: storeState.metricData.entity,
  loading: storeState.metricData.loading,
  updating: storeState.metricData.updating,
  updateSuccess: storeState.metricData.updateSuccess,
});

const mapDispatchToProps = {
  getStatuses,
  getMetrics,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricDataUpdate);
