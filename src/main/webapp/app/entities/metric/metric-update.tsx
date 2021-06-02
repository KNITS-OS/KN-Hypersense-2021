import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './metric.reducer';
import { IMetric } from 'app/shared/model/metric.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMetricUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MetricUpdate = (props: IMetricUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { metricEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/metric');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...metricEntity,
        ...values,
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
          <h2 id="smartfactoryApp.metric.home.createOrEditLabel" data-cy="MetricCreateUpdateHeading">
            Create or edit a Metric
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : metricEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="metric-id">ID</Label>
                  <AvInput id="metric-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="thingUuidLabel" for="metric-thingUuid">
                  Thing Uuid
                </Label>
                <AvField id="metric-thingUuid" data-cy="thingUuid" type="text" name="thingUuid" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/metric" replace color="info">
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
  metricEntity: storeState.metric.entity,
  loading: storeState.metric.loading,
  updating: storeState.metric.updating,
  updateSuccess: storeState.metric.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricUpdate);
